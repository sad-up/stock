package com.hh.stock.web.controller.system;

import com.hh.stock.common.constant.Constants;
import com.hh.stock.common.core.domain.AjaxResult;
import com.hh.stock.common.core.domain.entity.Permission;
import com.hh.stock.common.core.domain.entity.User;
import com.hh.stock.common.core.domain.model.LoginBody;
import com.hh.stock.common.core.domain.model.LoginUser;
import com.hh.stock.common.core.domain.model.RegisterBody;
import com.hh.stock.common.utils.SecurityUtils;
import com.hh.stock.common.utils.StringUtils;
import com.hh.stock.framework.web.service.SysLoginService;
import com.hh.stock.framework.web.service.SysPermissionService;
import com.hh.stock.framework.web.service.SysRegisterService;
import com.hh.stock.system.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

import static com.hh.stock.common.core.domain.AjaxResult.error;
import static com.hh.stock.common.core.domain.AjaxResult.success;

/**
 * @author : hh
 * @date : 2023/2/10 16:30
 * @description : 登录接口
 */
@RestController
@RequestMapping("/api")
public class SysLoginController {

    @Autowired
    private SysLoginService loginService;


    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private SysRegisterService registerService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @RequestMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody){
        AjaxResult ajaxResult = success();
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(), loginBody.getUuid());
        ajaxResult.put(Constants.TOKEN, token);
        return ajaxResult;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/getInfo")
    public AjaxResult getInfo(){
        User user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = sysPermissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = sysPermissionService.getMenuPermission(user);
        AjaxResult ajax = success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("/getRouters")
    public AjaxResult getRouters()
    {
        String userId = SecurityUtils.getUserId();
        List<Permission> menus = permissionService.findMenuTreeByUserId(userId);
        return success(permissionService.buildMenus(menus));
    }





    @PostMapping("/register")
    public AjaxResult register(@RequestBody RegisterBody user)
    {

        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }

}
