package com.hh.stock.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hh.stock.common.core.domain.entity.User;
import com.hh.stock.system.service.UserService;
import com.hh.stock.system.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Huanghe
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2023-02-11 18:34:14
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询用户信息列表
     */
    @Override
    public List<User> selectUsersList(User user) {
        return userMapper.selectUsersList(user);
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

//    @Override
//    public int updateUserProfile(User user) {
//        return 0;
//    }
}




