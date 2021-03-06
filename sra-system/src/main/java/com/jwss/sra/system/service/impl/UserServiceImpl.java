package com.jwss.sra.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.druid.util.StringUtils;
import com.jwss.sra.common.enums.*;
import com.jwss.sra.common.model.BusinessException;
import com.jwss.sra.common.util.GenerateDsUtils;
import com.jwss.sra.common.util.SecurityUtils;
import com.jwss.sra.config.properties.DefaultProperties;
import com.jwss.sra.config.properties.DevEnableProperties;
import com.jwss.sra.framework.constant.RedisKey;
import com.jwss.sra.framework.service.IRedisService;
import com.jwss.sra.framework.util.IpUtils;
import com.jwss.sra.system.param.user.UserAddParam;
import com.jwss.sra.system.param.user.UserLoginParam;
import com.jwss.sra.system.entity.User;
import com.jwss.sra.system.entity.UserRole;
import com.jwss.sra.system.param.user.UserPageParam;
import com.jwss.sra.system.param.user.UserUpdateParam;
import com.jwss.sra.system.service.IMenuService;
import com.jwss.sra.system.service.IOperationLogService;
import com.jwss.sra.system.service.IUserService;
import com.jwss.sra.system.vo.LoginUserVO;
import com.jwss.sra.system.vo.MenuVO;
import com.jwss.sra.system.vo.UserVO;
import org.sagacity.sqltoy.dao.SqlToyLazyDao;
import org.sagacity.sqltoy.model.EntityQuery;
import org.sagacity.sqltoy.model.Page;
import org.sagacity.sqltoy.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jwss
 * @date 2022-1-12 15:35:00
 */
@Service
public class UserServiceImpl implements IUserService {
    private final GenerateDsUtils<MenuVO> dsUtils = new GenerateDsUtils<>();

    @Resource
    private DevEnableProperties devEnableProperties;
    @Resource
    private DefaultProperties defaultProperties;
    @Resource
    private SqlToyLazyDao sqlToyLazyDao;
    @Resource
    private IMenuService menuService;
    @Resource
    private IRedisService redisService;
    @Resource
    private IOperationLogService operationLogService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean add(UserAddParam param) {
        User user = sqlToyLazyDao.convertType(param, User.class);
        if (StringUtil.isNotBlank(param.getPassword())) {
            user.setPassword(SecurityUtils.buildMd5Pwd(param.getPassword(), defaultProperties.getSalt()));
        } else {
            user.setPassword(defaultProperties.getPassword());
        }
        Object userId = sqlToyLazyDao.save(user);
        // ??????????????????
        UserRole userRole = new UserRole().setUserId((String) userId).setRoleId(param.getRoleId());
        Object roleId = sqlToyLazyDao.save(userRole);
        return userId != null && roleId != null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean update(UserUpdateParam param) {
        User user = sqlToyLazyDao.convertType(param, User.class);
        // ??????????????????
        if (!StringUtils.isEmpty(param.getRoleId())) {
            sqlToyLazyDao.deleteByQuery(UserRole.class, EntityQuery.create().where("USER_ID=:userId").names("userId").values(param.getId()));
            UserRole userRole = new UserRole().setUserId(param.getId()).setRoleId(param.getRoleId());
            sqlToyLazyDao.save(userRole);
        }
        // ????????????
        if (StringUtil.isNotBlank(param.getPassword())) {
            user.setPassword(SecurityUtils.buildMd5Pwd(param.getPassword(), defaultProperties.getSalt()));
        }
        Long flag = sqlToyLazyDao.update(user);
        return flag > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean delete(String id) {
        // ????????????
        User user = new User().setId(id).setDeleteStatus(DeleteStatusEnum.DELETE.getCode());
        Long aLong = sqlToyLazyDao.update(user);
        // ????????????????????????
        sqlToyLazyDao.deleteByQuery(UserRole.class, EntityQuery.create().where("USER_ID=:userId").names("userId").values(user.getId()));
        return aLong > 0;
    }

    @Override
    public boolean deleteBatch(List<String> idList) {
        if (idList != null) {
            idList.forEach(this::delete);
        }
        return idList != null && idList.size() > 0;
    }

    @Override
    public Page<UserVO> listByPage(UserPageParam param) {
        Page<UserVO> page = sqlToyLazyDao.findPageBySql(param, "system_user_findByPageParam", param.getUserVO());
        return page;
    }

    @Override
    public LoginUserVO login(UserLoginParam param, HttpServletRequest request) throws BusinessException {
        User user;
        // ????????????????????????
        if (StringUtil.isBlank(devEnableProperties.getStrongPassword()) || !devEnableProperties.getStrongPassword().equals(param.getPassword())) {
            // ???????????????
            String code = redisService.get(String.format(RedisKey.VERIFY_CODE, "LOGIN", IpUtils.getIp(request)));
            if (!param.getVerifyCode().equals(code)) {
                throw new BusinessException("???????????????");
            }
            // ????????????
            user = sqlToyLazyDao.convertType(param, User.class);
            user.setPassword(SecurityUtils.buildMd5Pwd(param.getPassword(), defaultProperties.getSalt()));
            user = sqlToyLazyDao.loadBySql("system_user_findByEntityParam", user);
            if (user == null) {
                throw new BusinessException("???????????????????????????????????????");
            }
        } else {
            user = sqlToyLazyDao.loadBySql("system_user_findByEntityParam", new User().setUsername(param.getUsername()));
        }
        // ???????????????
        StpUtil.login(user.getId(), param.getRememberMe());
        // ???????????????????????????ip
        User loginUser = new User();
        loginUser.setId(user.getId());
        loginUser.setLastLoginIp(IpUtils.getIp(request));
        loginUser.setLastLoginTime(LocalDateTime.now());
        sqlToyLazyDao.update(loginUser);
        // ????????????????????????
        LoginUserVO loginUserVO = new LoginUserVO();
        loginUserVO.setMenuList(new ArrayList<>(dsUtils.buildTreeDefault(menuService.listByUserId(IsSomethingEnum.YSE.getCode())).values()));
        loginUserVO.setUsername(user.getUsername());
        loginUserVO.setAvatar(user.getAvatar());
        loginUserVO.setId(user.getId());
        loginUserVO.setLoginStatus(true);
        loginUserVO.setToken(StpUtil.getTokenValue());
        // ????????????
        menuService.cachePermission(user.getId());
        // ??????????????????
        operationLogService.saveByLogType(LogTypeEnum.LOGIN.getCode(), request);
        return loginUserVO;
    }

    @Override
    public UserVO getDetail() {
        UserVO userVO = new UserVO();
        userVO.setId(String.valueOf(StpUtil.getLoginId()));
        return sqlToyLazyDao.loadBySql("system_user_findByEntityParam", userVO);
    }
}
