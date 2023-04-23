package com.hh.stock.framework.web.service;

import com.hh.stock.common.constant.CacheConstants;
import com.hh.stock.common.constant.Constants;
import com.hh.stock.common.core.domain.model.LoginUser;
import com.hh.stock.common.core.redis.RedisCache;
import com.hh.stock.common.exception.ServiceException;

import com.hh.stock.common.exception.user.CaptchaException;
import com.hh.stock.common.exception.user.CaptchaExpireException;
import com.hh.stock.common.exception.user.UserPasswordNotMatchException;
import com.hh.stock.common.utils.MessageUtils;
import com.hh.stock.common.utils.StringUtils;

import com.hh.stock.framework.manager.AsyncManager;
import com.hh.stock.framework.manager.factory.AsyncFactory;
import com.hh.stock.framework.security.context.AuthenticationContextHolder;
import com.hh.stock.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author : hh
 * @date : 2023/2/10 21:35
 * @description : 登录校验方法
 */
@Component
public class SysLoginService {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserService userService;

    @Resource
    private AuthenticationManager authenticationManager;
//    @Autowired
//    private ISysConfigService configService;

    public String login(String username, String password, String code, String uuid) {


        validateCaptcha(username, code, uuid);

        // 用户验证
        Authentication authentication = null;
        try{
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            AuthenticationContextHolder.setContext(authenticationToken);
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(authenticationToken);
        }catch (Exception e){
            if (e instanceof BadCredentialsException)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
//        recordLoginInfo(loginUser.getUserId());
        return tokenService.createToken(loginUser);
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid)
    {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(String userId)
    {
//        User user = new User();
//        user.setId(userId);
//        user.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
//        user.setLoginDate(DateUtils.getNowDate());
//        userService.updateUserProfile(user);
    }

}
