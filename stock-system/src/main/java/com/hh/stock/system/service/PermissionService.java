package com.hh.stock.system.service;

import com.hh.stock.common.core.domain.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hh.stock.system.domain.vo.RouterVo;

import java.util.List;
import java.util.Set;

/**
* @author Huanghe
* @description 针对表【sys_permission(权限表（菜单）)】的数据库操作Service
* @createDate 2023-02-11 18:35:22
*/
public interface PermissionService extends IService<Permission> {
    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> findMenuPermsByUserId(String userId);

    /**
     * 根据角色ID查询权限
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    Set<String> findMenuPermsByRoleId(String roleId);

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<Permission> findMenuTreeByUserId(String userId);


    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    public List<RouterVo> buildMenus(List<Permission> menus);


}
