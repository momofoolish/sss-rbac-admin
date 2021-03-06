package com.jwss.sra.system.service.impl;

import com.jwss.sra.common.model.BusinessException;
import com.jwss.sra.system.param.role.RoleAddParam;
import com.jwss.sra.system.param.role.RolePageParam;
import com.jwss.sra.system.param.role.RoleUpdateParam;
import com.jwss.sra.system.entity.Role;
import com.jwss.sra.system.entity.RoleMenu;
import com.jwss.sra.system.service.IRoleService;
import com.jwss.sra.system.vo.RoleMenuVO;
import com.jwss.sra.system.vo.RoleVO;
import org.sagacity.sqltoy.dao.SqlToyLazyDao;
import org.sagacity.sqltoy.model.EntityQuery;
import org.sagacity.sqltoy.model.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jwss
 */
@Service
public class RoleServiceImpl implements IRoleService {
    @Resource
    private SqlToyLazyDao sqlToyLazyDao;

    @Override
    public boolean add(RoleAddParam param) throws BusinessException {
        Role role = sqlToyLazyDao.convertType(param, Role.class);
        Role existRole = sqlToyLazyDao.loadBySql(
                "select ID from sys_role where #[ROLE_KEY=:roleKey] and DELETE_STATUS=1",
                role
        );
        if (existRole != null) {
            throw new BusinessException("已存在该角色标识");
        }
        Object id = sqlToyLazyDao.save(role);
        return id != null;
    }

    @Override
    public boolean deleteBatch(List<String> idList) {
        return false;
    }

    @Override
    public boolean update(RoleUpdateParam param) {
        Role role = sqlToyLazyDao.convertType(param, Role.class);
        Long update = sqlToyLazyDao.update(role);
        return update > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean grantPermissionsByRoleId(List<RoleMenuVO> roleMenuVOList) throws BusinessException {
        List<RoleMenu> roleMenuList = sqlToyLazyDao.convertType(roleMenuVOList, RoleMenu.class);
        if (roleMenuList.size() <= 0) {
            throw new BusinessException("集合为空");
        }
        // 先删除所有权限再设置
        sqlToyLazyDao.deleteByQuery(
                RoleMenu.class,
                EntityQuery.create()
                        .where("#[role_id=:roleId]").names("roleId").values(roleMenuList.get(0).getRoleId()));
        // 重新添加权限
        Long aLong = sqlToyLazyDao.saveOrUpdateAll(roleMenuList);
        return aLong > 0;
    }

    @Override
    public RoleVO loadByUserId(String userId) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("userId", userId);
        return sqlToyLazyDao.loadBySql("system_role_loadByUserId", map, RoleVO.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean delete(String id) {
        // 删除角色
        Long aLong = sqlToyLazyDao.delete(new Role().setId(id));
        // 删除角色权限关联关系
        sqlToyLazyDao.deleteByQuery(
                RoleMenu.class,
                EntityQuery.create().where("#[role_id = :roleId ]").names("roleId").values(id));
        return aLong > 0;
    }

    @Override
    public Page<RoleVO> listByPage(RolePageParam param) {
        Page<RoleVO> page = sqlToyLazyDao.findPageBySql(param, "system_role_findByPageParam", param.getRoleVO());
        return page;
    }
}
