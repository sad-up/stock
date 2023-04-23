package com.hh.stock.web.controller.system;

import com.hh.stock.common.constant.UserConstants;
import com.hh.stock.common.core.controller.BaseController;
import com.hh.stock.common.core.domain.AjaxResult;
import com.hh.stock.common.core.domain.entity.Role;
import com.hh.stock.common.core.domain.entity.User;
import com.hh.stock.common.core.domain.model.LoginUser;
import com.hh.stock.common.utils.StringUtils;
import com.hh.stock.framework.web.service.SysPermissionService;
import com.hh.stock.framework.web.service.TokenService;
import com.hh.stock.system.domain.UserRole;
import com.hh.stock.system.service.RoleService;
import com.hh.stock.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.hh.stock.common.utils.SecurityUtils.getUsername;

/**
 * @author : hh
 * @date : 2023/4/7 0:01
 * @description : 权限接口
 */
@RestController
@RequestMapping("/api/role")
public class SysRoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    /**
     * 获取权限列表
     * @param
     * @return
     */
    @GetMapping("/list")
    public AjaxResult rolelist(HttpServletRequest request, Integer page, Integer pageSize)
    {
        return roleService.getRoleList(request,page, pageSize);

    }

    /**
     * 删除角色
     */
    @DeleteMapping("/{roleIds}")
    public AjaxResult remove(@PathVariable String[] roleIds)
    {
        return toAjax(roleService.deleteRoleByIds(roleIds));
    }

    /**
     * 状态修改
     */

    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody Role role)
    {
        roleService.checkRoleAllowed(role);
        roleService.checkRoleDataScope(role.getId());
        role.setUpdateBy(getUsername());
        return toAjax(roleService.updateRoleStatus(role));
    }

    /**
     * 新增角色
     */
    @PostMapping
    public AjaxResult add(@Validated @RequestBody Role role)
    {
        if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role)))
        {
            return error("新增角色'" + role.getName() + "'失败，角色名称已存在");
        }
//        else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role)))
//        {
//            return error("新增角色'" + role.getName() + "'失败，角色权限已存在");
//        }
        role.setCreateBy(getUsername());
        return toAjax(roleService.insertRole(role));

    }

    /**
     * 修改保存角色
     */
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody Role role)
    {
        roleService.checkRoleAllowed(role);
        roleService.checkRoleDataScope(role.getRoleId());
        if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role)))
        {
            return error("修改角色'" + role.getName() + "'失败，角色名称已存在");
        }
//        else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role)))
//        {
//            return error("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
//        }
        role.setUpdateBy(getUsername());

        if (roleService.updateRole(role) > 0)
        {
            // 更新缓存用户权限
            LoginUser loginUser = getLoginUser();
            if (StringUtils.isNotNull(loginUser.getUser()) && !loginUser.getUser().isAdmin())
            {
                loginUser.setPermissions(sysPermissionService.getMenuPermission(loginUser.getUser()));
                loginUser.setUser(userService.selectUserByUserName(loginUser.getUser().getUsername()));
                tokenService.setLoginUser(loginUser);
            }
            return success();
        }
        return error("修改角色'" + role.getName() + "'失败，请联系管理员");
    }


    /**
     * 根据角色编号获取详细信息
     */
    @GetMapping(value = "/{roleId}")
    public AjaxResult getInfo(@PathVariable String roleId)
    {
        roleService.checkRoleDataScope(roleId);
        return success(roleService.selectRoleById(roleId));
    }

    /**
     * 查询已分配用户角色列表
     */
    @GetMapping("/authUser/allocatedList")
    public AjaxResult allocatedList(User user,Integer page, Integer pageSize)
    {
//        startPage();
//        List<User> list = userService.selectAllocatedList(user);
//        return getDataTable(list);
        return userService.selectAllocatedList(user, page, pageSize);
    }



    /**
     * 查询未分配用户角色列表
     */
    @GetMapping("/authUser/unallocatedList")
    public AjaxResult unallocatedList(User user, Integer page, Integer pageSize)
    {
//        startPage();
//        List<User> list = userService.selectUnallocatedList(user);
//        return getDataTable(list);
        return userService.selectUnallocatedList(user, page, pageSize);
    }

    /**
     * 取消授权用户
     */
    @PutMapping("/authUser/cancel")
    public AjaxResult cancelAuthUser(@RequestBody UserRole userRole)
    {
        return toAjax(roleService.deleteAuthUser(userRole));
    }

    /**
     * 批量取消授权用户
     */
    @PutMapping("/authUser/cancelAll")
    public AjaxResult cancelAuthUserAll(String roleId, String[] userIds)
    {
        return toAjax(roleService.deleteAuthUsers(roleId, userIds));
    }

    /**
     * 批量选择用户授权
     */
    @PutMapping("/authUser/selectAll")
    public AjaxResult selectAuthUserAll(String roleId, String[] userIds)
    {
        roleService.checkRoleDataScope(roleId);
        return toAjax(roleService.insertAuthUsers(roleId, userIds));
    }

}
