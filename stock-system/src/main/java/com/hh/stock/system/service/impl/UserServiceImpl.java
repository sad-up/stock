package com.hh.stock.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hh.stock.common.constant.UserConstants;
import com.hh.stock.common.core.domain.AjaxResult;
import com.hh.stock.common.core.domain.entity.User;
import com.hh.stock.common.core.domain.stockvo.PageResult;
import com.hh.stock.common.exception.ServiceException;
import com.hh.stock.common.utils.SecurityUtils;
import com.hh.stock.common.utils.StringUtils;
import com.hh.stock.common.utils.spring.SpringUtils;
import com.hh.stock.common.utils.uuid.IdWorker;
import com.hh.stock.system.domain.UserRole;
import com.hh.stock.system.mapper.UserRoleMapper;
import com.hh.stock.system.service.UserService;
import com.hh.stock.system.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private IdWorker idWorker;

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


    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public User selectUserByUserName(String userName)
    {
        return userMapper.selectUserByUserName(userName);
    }


    /**
     * 查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public AjaxResult selectAllocatedList(User user, Integer page, Integer pageSize)
    {
        // 1.设置分页参数
        PageHelper.startPage(page,pageSize);

        List<User> pages = userMapper.selectAllocatedList(user);
        if (CollectionUtils.isEmpty(pages)) {
            return AjaxResult.error(AjaxResult.ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        // 3.组装PageInfo对象,封装了一切分页信息
        PageInfo<User> pageInfo = new PageInfo<>(pages);
        // 4. 转化成自定义得分页对象
        PageResult<User> pageResult = new PageResult<>(pageInfo);
        return AjaxResult.success(pageResult);

    }

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public AjaxResult selectUserList(User user, Integer page, Integer pageSize)
    {
        // 1.设置分页参数
        PageHelper.startPage(page,pageSize);
//        return userMapper.selectUserList(user);
        List<User> pages = userMapper.selectUserList(user);
        if (CollectionUtils.isEmpty(pages)) {
            return AjaxResult.error(AjaxResult.ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        // 3.组装PageInfo对象,封装了一切分页信息
        PageInfo<User> pageInfo = new PageInfo<>(pages);
        // 4. 转化成自定义得分页对象
        PageResult<User> pageResult = new PageResult<>(pageInfo);
        return AjaxResult.success(pageResult);
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public String checkUserNameUnique(User user)
    {
        String userId = StringUtils.isNull(user.getId()) ? "": user.getId();
        User info = userMapper.checkUserNameUnique(user.getUsername());
        if (StringUtils.isNotNull(info) && !info.getId().equals(userId))
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkPhoneUnique(User user)
    {
        String userId = StringUtils.isNull(user.getId()) ? "" : user.getId();
        User info = userMapper.checkPhoneUnique(user.getPhone());
        if (StringUtils.isNotNull(info) && !info.getId().equals(userId))
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkEmailUnique(User user)
    {
        String userId = StringUtils.isNull(user.getId()) ? "" : user.getId();
        User info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getId().equals(userId))
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertUser(User user)
    {
        // 新增用户信息
        user.setId(idWorker.nextId()+"");
        int rows = userMapper.insertUser(user);
        // 新增用户与角色管理
        insertUserRole(user);
        return rows;
    }

    /**
     * 校验用户是否有数据权限
     *
     * @param userId 用户id
     */
    @Override
    public void checkUserDataScope(String userId)
    {
        if (!User.isAdmin(SecurityUtils.getUserId()))
        {
            User user = new User();
            user.setId(userId);
            List<User> users = SpringUtils.getAopProxy(this).selectUserList(user);
            if (StringUtils.isEmpty(users))
            {
                throw new ServiceException("没有权限访问用户数据！");
            }
        }
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public User selectUserById(String userId)
    {
        return userMapper.selectUserById(userId);
    }

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public List<User> selectUserList(User user)
    {
        return userMapper.selectUserList(user);
    }

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(User user)
    {
        if (StringUtils.isNotNull(user.getId()) && user.isAdmin())
        {
            throw new ServiceException("不允许操作超级管理员用户");
        }
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateUser(User user)
    {
        String userId = user.getId();

        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        return userMapper.updateUser(user);
    }

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserByIds(String[] userIds)
    {
        System.out.println(userIds);
        for (String userId : userIds)
        {
            checkUserAllowed(new User(userId));
            checkUserDataScope(userId);
        }
        // 删除用户与角色关联
        userRoleMapper.deleteUserRole(userIds);
        return userMapper.deleteUserByIds(userIds);
    }

    /**
     * 重置用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetPwd(User user)
    {

        return userMapper.updateUser(user);
    }

    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserStatus(User user)
    {
        return userMapper.updateUser(user);
    }


    /**
     * 用户授权角色
     *
     * @param userId 用户ID
     * @param roleIds 角色组
     */
    @Override
    @Transactional
    public void insertUserAuth(String userId, String[] roleIds)
    {
        userRoleMapper.deleteUserRoleByUserId(userId);
        insertUserRole(userId, roleIds);
    }


    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public AjaxResult selectUnallocatedList(User user, Integer page, Integer pageSize)
    {
//        return userMapper.selectUnallocatedList(user);
        // 1.设置分页参数
        PageHelper.startPage(page,pageSize);
//        return userMapper.selectUserList(user);
        List<User> pages = userMapper.selectUnallocatedList(user);
        if (CollectionUtils.isEmpty(pages)) {
            return AjaxResult.error(AjaxResult.ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        // 3.组装PageInfo对象,封装了一切分页信息
        PageInfo<User> pageInfo = new PageInfo<>(pages);
        // 4. 转化成自定义得分页对象
        PageResult<User> pageResult = new PageResult<>(pageInfo);
        return AjaxResult.success(pageResult);
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(User user)
    {
        this.insertUserRole(user.getId(), user.getRoleIds());
    }

    /**
     * 新增用户角色信息
     *
     * @param userId 用户ID
     * @param roleIds 角色组
     */
    public void insertUserRole(String userId, String[] roleIds)
    {
        if (StringUtils.isNotEmpty(roleIds))
        {
            // 新增用户与角色管理
            List<UserRole> list = new ArrayList<UserRole>(roleIds.length);
            for (String roleId : roleIds)
            {
                UserRole ur = new UserRole();
                ur.setId(idWorker.nextId()+"");
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                list.add(ur);
            }

            userRoleMapper.batchUserRole(list);
        }
    }
}




