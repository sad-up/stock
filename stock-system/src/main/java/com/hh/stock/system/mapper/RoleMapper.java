package com.hh.stock.system.mapper;

import com.hh.stock.common.core.domain.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author Huanghe
* @description 针对表【sys_role(角色表)】的数据库操作Mapper
* @createDate 2023-02-11 18:35:22
* @Entity com.hh.stock.domain.Role
*/
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> findRolePermissionByUserId(String userId);
}




