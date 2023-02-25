package com.hh.stock.system.service;

import com.hh.stock.common.core.domain.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
* @author Huanghe
* @description 针对表【sys_role(角色表)】的数据库操作Service
* @createDate 2023-02-11 18:35:22
*/
public interface RoleService extends IService<Role> {

    Set<String> findRolePermissionByUserId(String userId);
}
