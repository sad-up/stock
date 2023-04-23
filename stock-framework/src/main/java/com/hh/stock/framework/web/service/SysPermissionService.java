package com.hh.stock.framework.web.service;

import cn.hutool.core.collection.CollUtil;
import com.hh.stock.common.core.domain.entity.User;
import com.hh.stock.common.core.domain.entity.Role;
import com.hh.stock.system.service.PermissionService;
import com.hh.stock.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author : hh
 * @date : 2023/2/13 17:04
 * @description : 用户权限处理
 */

@Component
public class SysPermissionService
{
    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 获取角色数据权限
     *
     * @param user 用户信息
     * @return 角色权限信息
     */
    public Set<String> getRolePermission(User user)
    {
        Set<String> roles = new HashSet<String>();
        // 管理员拥有所有权限
        if (user.isAdmin())
        {
            roles.add("admin");
        }
        else
        {
            roles.addAll(roleService.findRolePermissionByUserId(user.getId()));
        }
        return roles;
    }

    /**
     * 获取菜单数据权限
     *
     * @param user 用户信息
     * @return 菜单权限信息
     */
    public Set<String> getMenuPermission(User user)
    {
        Set<String> perms = new HashSet<String>();
        // 管理员拥有所有权限
        if (user.isAdmin())
        {
            perms.add("*:*:*");
        }
        else
        {
            List<Role> roles = user.getRoles();
            if (CollUtil.isNotEmpty(roles) && roles.size() >= 1)
            {
                // 多角色设置permissions属性，以便数据权限匹配权限
                for (Role role : roles)
                {
                    Set<String> rolePerms = permissionService.findMenuPermsByRoleId(role.getId());
                    role.setPermissions(rolePerms);
                    perms.addAll(rolePerms);
                }
            }
            else
            {
                perms.addAll(permissionService.findMenuPermsByUserId(user.getId()));
            }
        }
        return perms;
    }
}
