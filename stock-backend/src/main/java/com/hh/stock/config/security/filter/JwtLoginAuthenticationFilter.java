package com.hh.stock.config.security.filter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.hh.stock.config.security.utils.JwtTokenUtil;

import com.hh.stock.domain.sys.User;
import com.hh.stock.vo.resp.LoginRespVo;
import com.hh.stock.vo.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author : hh
 * @date : 2023/2/2 19:07
 * @description : 自定义登录拦截过滤器
 */

public class JwtLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {



    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 通过构造器传入指定的登录url地址
     * @param loginUrl 自定义url登录地址
     */
    public JwtLoginAuthenticationFilter(String loginUrl) {
        super(loginUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String username = null;
        String password = null;
        String code = null;
        String uuid = null;

        // 1.判断当前请求是否为ajax json方式请求 equalsIgnoreCase方法：比较字符串忽略大小写
        if ((request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_UTF8_VALUE)
                || request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_UTF8_VALUE))
                 && request.getMethod().equalsIgnoreCase("POST")
        ) {
            // 2.获取请求流中得用户信息
            // 2.1 从request对象中获取流对象
            ServletInputStream in = request.getInputStream();
            // 2.2 解析流，获取用户相关的信息，暂时封装到HashMap
            HashMap<String, String> mapInfo = new ObjectMapper().readValue(in, HashMap.class);
            // 2.3 获取用户名和密码信息
            username = mapInfo.get("username");
            System.out.println(username);
            password = mapInfo.get("password");
            code = mapInfo.get("code");
            uuid = "captcha_codes:" + mapInfo.get("uuid");

        }
        else {
            username = request.getParameter("username");
            password = request.getParameter("password");
            code = request.getParameter("code");
            uuid = "captcha_codes:" + request.getParameter("uuid");
        }
        String uuidValue = (String) redisTemplate.opsForValue().get(uuid);
        if(uuidValue == null || !uuidValue.equals(code)){
            throw  new RuntimeException("验证码错误");
        }
        //删除验证码
//        redisTemplate.delete(uuid);
        // 3.将用户信息组装成一个token对象
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        //4.将组装的token对象委托给认证管理器做后续的认证处理
        // 认证管理器底层会调用UserDetaiLService 方法获取数据库中的用户信息，然后与token对象下的信息进行密码比对，如果一致，则
        // 将数据库查询的权限集合信息封装到oken对象下; private fina Collection<GrantedAuthoritu> authorities;
        // 这样，用户在访问资源时，会带着这个tokne对象，访向什么资源，就从token中判断用户是否用户对资源的权限
        return this.getAuthenticationManager().authenticate(token);

    }

    /**
     * 认证成功处理方法
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */


    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        // 获取用户信息
        User user = (User) authResult.getPrincipal();
        // 从User中获取权限信息
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        // 基于JWT相应token数据信息
        String token = JwtTokenUtil.createToken(user.getUsername(), authorities.toString());

        // 设置响应编码格式
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        // 组装响应结果
        LoginRespVo loginResult = LoginRespVo.builder()
                .nickName(user.getNickName())
                .avatar(user.getAvatar())
                .username(user.getUsername())
                .phone(user.getPhone())
                .menus(user.getMenus())
                .permissions(user.getPermissions())
                .token(token)
                .build();

        R<LoginRespVo> ok = R.ok(loginResult);
        //转化成json字符串响应前端
        String result = JSON.toJSONString(ok);
        // 响应数据
        response.getWriter().write(result);
    }






    /**
     * 认证失败后的处理方法
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        //组装响应数据
        HashMap<String, String> info = new HashMap<>();
        info.put("error",failed.getMessage());
        String errorJson = JSON.toJSONString(info);
        //响应数据
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        R result = R.error(errorJson);
        response.getWriter().write(JSON.toJSONString(result));
    }
}
