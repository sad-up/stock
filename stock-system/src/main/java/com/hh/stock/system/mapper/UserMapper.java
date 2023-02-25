package com.hh.stock.system.mapper;

import com.hh.stock.common.core.domain.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author Huanghe
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2023-02-11 18:34:13
* @Entity com.hh.stock.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询用户信息列表
     */
    List<User> selectUsersList(User user);

    User findByUsername(String username);
}




