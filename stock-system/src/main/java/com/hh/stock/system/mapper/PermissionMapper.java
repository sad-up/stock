package com.hh.stock.system.mapper;

import com.hh.stock.common.core.domain.AjaxResult;
import com.hh.stock.common.core.domain.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
* @author Huanghe
* @description 针对表【sys_permission(权限表（菜单）)】的数据库操作Mapper
* @createDate 2023-02-11 18:35:22
* @Entity com.hh.stock.domain.Permission
*/
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 根据用户查询系统菜单列表
     *
     * @param userId 菜单信息
     * @return 菜单列表
     */
    List<String> findMenuPermsByUserId(String userId);

    /**
     * 查询系统菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    public List<Permission> selectMenuList(Permission menu);

    /**
     * 根据角色ID查询权限
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
     List<String> findMenuPermsByRoleId(String  roleId);

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
     List<Permission> findMenuTreeByUserId(String userId);



    /**
     * 根据用户ID查询菜单
     *
     * @return 菜单列表
     */
    List<Permission> findMenuTreeAll(String userId);

    /**
     * 根据用户查询系统菜单列表
     *
     * @param permission 菜单信息
     * @return 菜单列表
     */
    public List<Permission> selectMenuListByUserId(Permission permission);

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @param menuCheckStrictly 菜单树选择项是否关联显示
     * @return 选中菜单列表
     */
    public List<String> selectMenuListByRoleId(@Param("roleId") String roleId, @Param("menuCheckStrictly") boolean menuCheckStrictly);

    /**
     * 校验菜单名称是否唯一
     *
     * @param title 菜单名称
     * @param pid 父菜单ID
     * @return 结果
     */
    Permission checkMenuNameUnique(@Param("title") String title,@Param("pid") String pid);

    /**
     * 新增菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    int insertMenu(Permission menu);

    /**
     * 修改菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    int updateMenu(Permission menu);

    /**
     * 是否存在菜单子节点
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    int hasChildByMenuId(String menuId);

    /**
     * 删除菜单管理信息
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    int deleteMenuById(String menuId);

    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    Permission selectMenuById(String menuId);
}




