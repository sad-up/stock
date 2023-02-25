package com.hh.stock.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hh.stock.system.domain.RolePermission;
import com.hh.stock.system.service.RolePermissionService;
import com.hh.stock.system.mapper.RolePermissionMapper;
import org.springframework.stereotype.Service;

/**
* @author Huanghe
* @description 针对表【sys_role_permission(角色权限表)】的数据库操作Service实现
* @createDate 2023-02-11 18:35:22
*/
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission>
    implements RolePermissionService{

}




