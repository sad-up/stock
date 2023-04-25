package com.hh.stock.system.mapper;

import com.hh.stock.common.core.domain.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public User selectUserByUserName(String userName);

    /**
     * 根据条件分页查询已配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<User> selectAllocatedList(User user);

    /**
     * 根据条件分页查询用户列表
     *
     * @param  user 用户信息
     * @return 用户信息集合信息
     */
    List<User> selectUserList(User user);

    /**
     * 校验用户名称是否唯一
     *
     * @param Username 用户名称
     * @return 结果
     */
     User checkUserNameUnique(String Username);

    /**
     * 校验手机号码是否唯一
     *
     * @param phone 手机号码
     * @return 结果
     */
    User checkPhoneUnique(String phone);

    /**
     * 校验email是否唯一
     *
     * @param email 用户邮箱
     * @return 结果
     */
    User checkEmailUnique(String email);

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int insertUser(User user);


    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    User selectUserById(String userId);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(User user);

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    int deleteUserByIds(String[] userIds);


    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    List<User> selectUnallocatedList(User user);

    /**
     * 重置用户密码
     *
     * @param username 用户名
     * @param password 密码
     * @return 结果
     */
    public int resetUserPwd(@Param("username") String username, @Param("password") String password);
}




