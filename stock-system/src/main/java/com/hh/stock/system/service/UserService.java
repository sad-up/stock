package com.hh.stock.system.service;

import com.hh.stock.common.core.domain.AjaxResult;
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



    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public User selectUserByUserName(String userName);

    /**
     * 查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    AjaxResult selectAllocatedList(User user, Integer page, Integer pageSize);

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    AjaxResult selectUserList(User user, Integer page, Integer pageSize);



    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    String checkUserNameUnique(User user);

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    public String checkPhoneUnique(User user);

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    String checkEmailUnique(User user);



    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int insertUser(User user);

    /**
     * 校验用户是否有数据权限
     *
     * @param userId 用户id
     */
    void checkUserDataScope(String userId);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    User selectUserById(String userId);

    /**
     * 通过用户ID批量查询用户
     *
     * @param userIds 用户ID
     * @return 用户对象信息
     */
    List selectUserByIds(String[] userIds);


    List<User> selectUserList(User user);

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    public void checkUserAllowed(User user);

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
     * 重置用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    int resetPwd(User user);

    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    int updateUserStatus(User user);

    /**
     * 用户授权角色
     *
     * @param userId 用户ID
     * @param roleIds 角色组
     */
    void insertUserAuth(String userId, String[] roleIds);

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */

    AjaxResult selectUnallocatedList(User user, Integer page, Integer pageSize);

    /**
     * 注册用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    boolean registerUser(User user);

    /**
     * 根据用户ID查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */
    public String selectUserRoleGroup(String userName);

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */

    int updateUserProfile(User user);

    /**
     * 重置用户密码
     *
     * @param username 用户名
     * @param password 密码
     * @return 结果
     */
    public int resetUserPwd(String username, String password);
}
