package com.hh.stock.web.controller.system;

import com.hh.stock.common.config.vo.StockConfig;
import com.hh.stock.common.constant.UserConstants;
import com.hh.stock.common.core.domain.AjaxResult;
import com.hh.stock.common.core.domain.entity.User;
import com.hh.stock.common.core.domain.model.LoginUser;
import com.hh.stock.common.utils.SecurityUtils;
import com.hh.stock.common.utils.StringUtils;
import com.hh.stock.framework.web.service.TokenService;
import com.hh.stock.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.hh.stock.common.core.domain.AjaxResult.error;
import static com.hh.stock.common.core.domain.AjaxResult.success;
import static com.hh.stock.common.utils.SecurityUtils.getLoginUser;

/**
 * @author : hh
 * @date : 2023/4/25 15:15
 * @description : 个人信息 业务处理
 */
@RestController
@RequestMapping("/api/user/profile")
public class SysProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;


    /**
     * 个人信息
     */
    @GetMapping
    public AjaxResult profile()
    {
        LoginUser loginUser = getLoginUser();
        User user = loginUser.getUser();
        AjaxResult ajax = success(user);
        ajax.put("roleGroup", userService.selectUserRoleGroup(loginUser.getUsername()));
        return ajax;
    }

    /**
     * 修改用户
     */

    @PutMapping
    public AjaxResult updateProfile(@RequestBody User user)
    {
        LoginUser loginUser = getLoginUser();
        User sysUser = loginUser.getUser();
        user.setUsername(sysUser.getUsername());
        if (StringUtils.isNotEmpty(user.getPhone())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return error("修改用户'" + user.getUsername() + "'失败，手机号码已存在");
        }
        if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return error("修改用户'" + user.getUsername() + "'失败，邮箱账号已存在");
        }
        user.setId(sysUser.getId());
        user.setPassword(null);
        user.setAvatar(null);
        if (userService.updateUserProfile(user) > 0)
        {
            // 更新缓存用户信息
            sysUser.setNickName(user.getNickName());
            sysUser.setPhone(user.getPhone());
            sysUser.setEmail(user.getEmail());
            sysUser.setSex(user.getSex());
            tokenService.setLoginUser(loginUser);
            return success();
        }
        return error("修改个人信息异常，请联系管理员");
    }

    /**
     * 重置密码
     */
    @PutMapping("/updatePwd")
    public AjaxResult updatePwd(String oldPassword, String newPassword)
    {
        LoginUser loginUser = getLoginUser();
        String username = loginUser.getUsername();
        String password = loginUser.getPassword();
        if (!SecurityUtils.matchesPassword(oldPassword, password))
        {
            return error("修改密码失败，旧密码错误");
        }
        if (SecurityUtils.matchesPassword(newPassword, password))
        {
            return error("新密码不能与旧密码相同");
        }
        if (userService.resetUserPwd(username, SecurityUtils.encryptPassword(newPassword)) > 0)
        {
            // 更新缓存用户密码
            loginUser.getUser().setPassword(SecurityUtils.encryptPassword(newPassword));
            tokenService.setLoginUser(loginUser);
            return success();
        }
        return error("修改密码异常，请联系管理员");
    }

    /**
     * 头像上传
     */

//    @PostMapping("/avatar")
//    public AjaxResult avatar(@RequestParam("avatarfile") MultipartFile file) throws Exception
//    {
//        if (!file.isEmpty())
//        {
//            LoginUser loginUser = getLoginUser();
//            String avatar = FileUploadUtils.upload(StockConfig.getAvatarPath(), file, MimeTypeUtils.IMAGE_EXTENSION);
//            if (userService.updateUserAvatar(loginUser.getUsername(), avatar))
//            {
//                AjaxResult ajax = success();
//                ajax.put("imgUrl", avatar);
//                // 更新缓存用户头像
//                loginUser.getUser().setAvatar(avatar);
//                tokenService.setLoginUser(loginUser);
//                return ajax;
//            }
//        }
//        return error("上传图片异常，请联系管理员");
//    }
}
