package com.hh.stock.mapper.sys;

import com.hh.stock.domain.sys.RolePermission;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Huanghe
* @description 针对表【sys_role_permission(角色权限表)】的数据库操作Mapper
* @createDate 2023-01-31 09:27:03
* @Entity com.hh.stock.domain.sys.RolePermission
*/
@Mapper
public interface RolePermissionMapper {

    int deleteByPrimaryKey(Long id);

    int insert(RolePermission record);

    int insertSelective(RolePermission record);

    RolePermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RolePermission record);

    int updateByPrimaryKey(RolePermission record);

}
