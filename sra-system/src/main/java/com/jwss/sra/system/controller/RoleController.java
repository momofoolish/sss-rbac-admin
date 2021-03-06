package com.jwss.sra.system.controller;

import com.jwss.sra.common.model.ApiResult;
import com.jwss.sra.common.model.BusinessException;
import com.jwss.sra.system.param.role.RoleAddParam;
import com.jwss.sra.system.param.role.RolePageParam;
import com.jwss.sra.system.param.role.RoleUpdateParam;
import com.jwss.sra.system.service.IRoleService;
import com.jwss.sra.system.vo.RoleMenuVO;
import com.jwss.sra.system.vo.RoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.sagacity.sqltoy.model.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author jwss
 */
@Api(tags = "系统角色接口")
@Validated
@RequestMapping("/role")
@RestController
public class RoleController {
    @Resource
    private IRoleService roleService;

    @ApiOperation(value = "新增角色")
    @PostMapping("/add")
    public ApiResult<String> add(@Valid @RequestBody RoleAddParam param) throws BusinessException {
        boolean b = roleService.add(param);
        return ApiResult.flag(b);
    }

    @ApiOperation(value = "更新角色")
    @PostMapping("/update")
    public ApiResult<String> update(@Valid @RequestBody RoleUpdateParam param) throws BusinessException {
        boolean b = roleService.update(param);
        return ApiResult.flag(b);
    }

    @ApiOperation(value = "删除角色")
    @PostMapping("/delete/{id}")
    public ApiResult<String> delete(@PathVariable String id) throws BusinessException {
        boolean b = roleService.delete(id);
        return ApiResult.flag(b);
    }

    @ApiOperation(value = "给角色赋予权限")
    @PostMapping("/grantPermissionsByRoleId")
    public ApiResult<String> grantPermissionsByRoleId(@Valid @RequestBody List<RoleMenuVO> param) throws BusinessException {
        boolean b = roleService.grantPermissionsByRoleId(param);
        return ApiResult.flag(b);
    }

    @ApiOperation(value = "分页获取角色")
    @PostMapping("/listByPage")
    public ApiResult<Page<RoleVO>> listByPage(@Valid @RequestBody RolePageParam param) throws BusinessException {
        Page<RoleVO> p = roleService.listByPage(param);
        return ApiResult.ok(p);
    }
}
