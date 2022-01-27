package com.jwss.sra.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.jwss.sra.common.enums.AccountStatusEnum;
import com.jwss.sra.common.enums.IsSomethingEnum;
import com.jwss.sra.common.enums.SexEnum;
import com.jwss.sra.common.model.BusinessException;
import com.jwss.sra.system.param.user.UserAddParam;
import com.jwss.sra.system.param.user.UserLoginParam;
import com.jwss.sra.system.entity.User;
import com.jwss.sra.system.entity.UserRole;
import com.jwss.sra.system.param.user.UserPageParam;
import com.jwss.sra.system.service.IMenuService;
import com.jwss.sra.system.service.IUserService;
import com.jwss.sra.system.vo.LoginUserVO;
import com.jwss.sra.system.vo.UserVO;
import org.sagacity.sqltoy.dao.SqlToyLazyDao;
import org.sagacity.sqltoy.model.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @date 2022-1-12 15:35:00
 * @author jwss
 */
@Service
public class UserServiceImpl implements IUserService {
    @Resource
    private SqlToyLazyDao sqlToyLazyDao;

    @Resource
    private IMenuService menuService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean add(UserAddParam param) {
        User user = sqlToyLazyDao.convertType(param, User.class);
        user.setNickname(String.format("SRA-%s", System.currentTimeMillis()))
                .setSex(SexEnum.UNKNOWN.getCode())
                .setAccountStatus(AccountStatusEnum.NORMAL.getCode());
        Object userId = sqlToyLazyDao.save(user);
        // 授予用户角色
        UserRole userRole = new UserRole().setUserId((String) userId).setRoleId(param.getRoleId());
        Object roleId = sqlToyLazyDao.save(userRole);
        return userId != null && roleId != null;
    }

    @Override
    public Page<UserVO> listByPage(UserPageParam param) {
        Page<UserVO> page = sqlToyLazyDao.findPageBySql(param, "system_user_findByEntityParam", param.getUserVO());
        return page;
    }

    @Override
    public LoginUserVO login(UserLoginParam param) throws BusinessException {
        User user = sqlToyLazyDao.convertType(param, User.class);
        user = sqlToyLazyDao.loadBySql("system_user_findByEntityParam", user);
        if (user == null) {
            throw new BusinessException("登录失败，用户名或密码错误");
        }
        // 默认记住我模式
        StpUtil.login(user.getId(), true);
        // 返回用户登录信息
        LoginUserVO loginUserVO = new LoginUserVO();
        loginUserVO.setLoginStatus(true);
        loginUserVO.setToken(StpUtil.getTokenValue());
        loginUserVO.setUserDetail(sqlToyLazyDao.convertType(user, UserVO.class));
        loginUserVO.setPermissions(menuService.listByUserId(IsSomethingEnum.YSE.getCode()));
        // TODO 缓存权限
        return loginUserVO;
    }
}