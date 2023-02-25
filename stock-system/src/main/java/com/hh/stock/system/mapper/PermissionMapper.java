package com.hh.stock.system.mapper;

import com.hh.stock.common.core.domain.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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
     * @param menu 菜单信息
     * @return 菜单列表
     */
    List<String> findMenuPermsByUserId(String userId);

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
}




