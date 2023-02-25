package com.hh.stock.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hh.stock.system.domain.UserRole;
import com.hh.stock.system.service.UserRoleService;
import com.hh.stock.system.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author Huanghe
* @description 针对表【sys_user_role(用户角色表)】的数据库操作Service实现
* @createDate 2023-02-11 18:35:22
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




