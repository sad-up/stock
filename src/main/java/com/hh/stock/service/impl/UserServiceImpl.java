package com.hh.stock.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hh.stock.domain.User;
import com.hh.stock.service.UserService;
import com.hh.stock.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author Huanghe
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2023-02-11 18:31:29
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




