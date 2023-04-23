package com.hh.stock.system.service;

import com.hh.stock.common.core.domain.AjaxResult;
import com.hh.stock.common.core.domain.TreeSelect;
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
     * 根据询菜单树信息用户ID查
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

    /**
     * 根据用户查询系统菜单列表
     *
     * @param menu 菜单信息
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<Permission> selectMenuList(Permission menu, String userId);

    /**
     * 根据用户查询系统菜单列表
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<Permission> selectMenuList(String  userId);


    /**
     * 构建前端所需要树结构
     *
     * @param menus 菜单列表
     * @return 树结构列表
     */
    public List<Permission> buildMenuTree(List<Permission> menus);
    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildMenuTreeSelect(List<Permission> menus);

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    public List<String> selectMenuListByRoleId(String roleId);



    /**
     * 是否存在菜单子节点
     *
     * @param menuId 菜单ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean hasChildByMenuId(String menuId);
//
//    /**
//     * 查询菜单是否存在角色
//     *
//     * @param menuId 菜单ID
//     * @return 结果 true 存在 false 不存在
//     */
//    public boolean checkMenuExistRole(String menuId);
//
    /**
     * 新增保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public int insertMenu(Permission menu);
//
    /**
     * 修改保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public int updateMenu(Permission menu);

    /**
     * 删除菜单管理信息
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    public int deleteMenuById(String menuId);

    /**
     * 校验菜单名称是否唯一
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public String checkMenuNameUnique(Permission menu);

    /**
     * 查询菜单是否存在角色
     *
     * @param menuId 菜单ID
     * @return 结果 true 存在 false 不存在
     */
    boolean checkMenuExistRole(String menuId);

    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    Permission selectMenuById(String menuId);
}
