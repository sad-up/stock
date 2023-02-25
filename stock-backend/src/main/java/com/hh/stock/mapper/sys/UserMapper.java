package com.hh.stock.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hh.stock.domain.sys.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author Huanghe
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2023-01-31 09:27:03
* @Entity com.hh.stock.domain.sys.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 根据用户名称查询用户信息
     * @param username
     * @return
     */
    User findUserInfoByUserName(@Param("username") String username);
}
