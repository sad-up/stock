package com.hh.stock.framework.web.service;

import com.hh.stock.common.core.domain.entity.User;
import com.hh.stock.common.core.domain.model.LoginUser;
import com.hh.stock.common.enums.UserStatus;
import com.hh.stock.common.exception.ServiceException;
import com.hh.stock.common.utils.StringUtils;
import com.hh.stock.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : hh
 * @date : 2023/2/10 21:49
 * @description : 用户验证处理
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SysPermissionService sysPermissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (StringUtils.isNull(user))
        {
            log.info("登录用户：{} 不存在.", username);
            throw new ServiceException("登录用户：" + username + " 不存在");
        }
        else if (UserStatus.DELETED.getCode().equals(user.getDeleted()))
        {
            log.info("登录用户：{} 已被删除.", username);
            throw new ServiceException("对不起，您的账号：" + username + " 已被删除");
        }
        else if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
        {
            log.info("登录用户：{} 已被停用.", username);
            throw new ServiceException("对不起，您的账号：" + username + " 已停用");
        }


        return createLoginUser(user);
    }

    public  UserDetails createLoginUser(User user){
        return new LoginUser(user.getId(),user, sysPermissionService.getMenuPermission(user));
    }



}
