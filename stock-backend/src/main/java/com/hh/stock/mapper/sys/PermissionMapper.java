package com.hh.stock.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hh.stock.domain.sys.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author Huanghe
* @description 针对表【sys_permission(权限表（菜单）)】的数据库操作Mapper
* @createDate 2023-01-31 09:27:02
* @Entity com.hh.stock.domain.sys.Permission
*/
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    /**
     * 根据用户ID 查询权限菜单列表
     */
    List<Permission> findPermissionListById(String userId);


}
