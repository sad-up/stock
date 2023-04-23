package com.hh.stock.web.controller.system;

import com.hh.stock.common.constant.UserConstants;
import com.hh.stock.common.core.controller.BaseController;
import com.hh.stock.common.core.domain.AjaxResult;
import com.hh.stock.common.core.domain.entity.Role;
import com.hh.stock.common.core.domain.entity.User;
import com.hh.stock.common.utils.SecurityUtils;
import com.hh.stock.common.utils.StringUtils;
import com.hh.stock.system.service.RoleService;
import com.hh.stock.system.service.UserService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.hh.stock.common.core.controller.BaseController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;




/**
 * @author : hh
 * @date : 2023/4/17 8:51
 * @description : 用户信息
 */

@RestController
@RequestMapping("/api/user")
public class SysUserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 获取用户列表
     */
    @GetMapping("/list")
    public AjaxResult list(User user, Integer page, Integer pageSize)
    {
//        List<SysUser> list = userService.selectUserList(user);
//        return getDataTable(list);
        return userService.selectUserList(user, page, pageSize);
    }

    /**
     * 新增用户
     */
    @PostMapping
    public AjaxResult add(@Validated @RequestBody User user)
    {
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user)))
        {
            return error("新增用户'" + user.getUsername() + "'失败，登录账号已存在");
        }
        else if (StringUtils.isNotEmpty(user.getPhone())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return error("新增用户'" + user.getUsername() + "'失败，手机号码已存在");
        }
        else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return error("新增用户'" + user.getUsername() + "'失败，邮箱账号已存在");
        }
        user.setCreateBy(getUsername());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return toAjax(userService.insertUser(user));
    }

    /**
     * 根据用户编号获取详细信息
     */
    @GetMapping(value = { "/", "/{userId}" })
    public AjaxResult getInfo(@PathVariable(value = "userId", required = false) String userId)
    {
        userService.checkUserDataScope(userId);
        AjaxResult ajax = AjaxResult.success();
        List<Role> roles = roleService.selectRoleAll();
        ajax.put("roles", User.isAdmin(userId) ? roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()) : roles);
        if (StringUtils.isNotNull(userId))
        {
            User sysUser = userService.selectUserById(userId);
            ajax.put(AjaxResult.DATA_TAG, sysUser);
            ajax.put("roleIds", StringUtils.isNotNull(sysUser.getRoles()) ? sysUser.getRoles().stream().map(Role::getRoleId).collect(Collectors.toList()): "");
        }
        return ajax;
    }



    /**
     * 修改用户
     */
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody User user)
    {
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getId());
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user)))
        {
            return error("修改用户'" + user.getUsername() + "'失败，登录账号已存在");
        }
        else if (StringUtils.isNotEmpty(user.getPhone())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return error("修改用户'" + user.getUsername() + "'失败，手机号码已存在");
        }
        else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return error("修改用户'" + user.getUsername() + "'失败，邮箱账号已存在");
        }
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setUpdateBy(getUsername());
        return toAjax(userService.updateUser(user));
    }

    /**
     * 删除用户
     */

    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable String[] userIds)
    {
        System.out.println(userIds);
        if (ArrayUtils.contains(userIds, getUserId()))
        {
            return error("当前用户不能删除");
        }
        return toAjax(userService.deleteUserByIds(userIds));
    }

    /**
     * 重置密码
     */

    @PutMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody User user)
    {
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getId());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setUpdateBy(getUsername());
        return toAjax(userService.resetPwd(user));
    }

    /**
     * 状态修改
     */

    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody User user)
    {
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getId());
        user.setUpdateBy(getUsername());
        return toAjax(userService.updateUserStatus(user));
    }

    /**
     * 根据用户编号获取授权角色
     */
    @GetMapping("/authRole/{userId}")
    public AjaxResult authRole(@PathVariable("userId") String userId)
    {
        AjaxResult ajax = AjaxResult.success();
        User user = userService.selectUserById(userId);
        List<Role> roles = roleService.selectRolesByUserId(userId);
        ajax.put("user", user);
        ajax.put("roles", User.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        return ajax;
    }

    /**
     * 用户授权角色
     */
    @PutMapping("/authRole")
    public AjaxResult insertAuthRole(String userId, String[] roleIds)
    {
        userService.checkUserDataScope(userId);
        userService.insertUserAuth(userId, roleIds);
        return success();
    }

}
