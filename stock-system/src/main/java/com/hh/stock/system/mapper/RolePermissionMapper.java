package com.hh.stock.system.mapper;

import com.hh.stock.system.domain.RolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Huanghe
* @description 针对表【sys_role_permission(角色权限表)】的数据库操作Mapper
* @createDate 2023-02-11 18:35:22
* @Entity com.hh.stock.domain.RolePermission
*/
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

}




