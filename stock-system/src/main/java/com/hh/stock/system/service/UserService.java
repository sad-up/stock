package com.hh.stock.system.service;

import com.hh.stock.common.core.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Huanghe
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2023-02-11 18:34:14
*/
public interface UserService extends IService<User> {

    /**
     * 查询用户信息列表
     */
    List<User> selectUsersList(User user);

    User findByUsername(String username);

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
//    int updateUserProfile(User user);
}
