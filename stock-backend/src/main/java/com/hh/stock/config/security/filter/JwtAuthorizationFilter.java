package com.hh.stock.config.security.filter;

import com.hh.stock.config.security.handler.LoginFailureHandler;
import com.hh.stock.config.security.utils.JwtTokenUtil;
import com.hh.stock.utils.StringUtils;
import io.jsonwebtoken.Claims;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author : hh
 * @date : 2023/2/2 20:09
 * @description : 过滤用户请求，获取请求头中携带的token,用于用户的权限校验
 */

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private LoginFailureHandler loginFailureHandler;


    //获取登录请求地址
    @Value("${request.login.url}")
    private String loginUrl;

    @Value("${request.captchaImage.url}")
    private String captchaImageUrl;
    /**
     * 定义过滤规则
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {



        // 1. 获取请求头中的token信息
        String token = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        // 2. 判断token是否存在，如果不存在
        // 2.1 如果token不存在，则放行请求，去完成认证登录
        String url = request.getRequestURI();
        try{
            // 判断当前请求是否是登录请求，如果不是登陆请求，则需要验证token
            if ((token == null || token.trim() == null)  && (url.equals(loginUrl) || url.equals(captchaImageUrl))) {
                //直接放行，也就意味着当前请求无法生成UsernamePasswordAuthenticationToken对象，那么后续的过减器在进行
                // 权限校验时，肯定不通过
                doFilter(request,response,filterChain);
                //终止当前操作
                return;
            }
            // 3. 如果token存在，解析token，获取用户名和权限信息
            Claims claims = JwtTokenUtil.checkJWT(token);
            if (claims == null) {
                // 3.1 说明token已经失效
                response.getWriter().write("token失效");
                return;
            }
            // 3.2 获取用户名和权限信息
            String username = JwtTokenUtil.getUsername(token);
            if(username == null){
                throw new RuntimeException("tocken无效");
            }
            // 格式 ROLE_ADMIN，p6
            // 获得用户角色权限集合字符串
            String role = JwtTokenUtil.getUserRole(token);
            // 获取以逗号间隔的权限字符串
            String roleNames = StringUtils.strip(role, "[]");
            List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(roleNames);

            // 4. 将用户名和权限信息组装成UsernamePassowrdAuthenticationToken对象
            /**
             * 参数1:用户名
             * 参数2:密码
             * 参数3:权限集合
             */
            UsernamePasswordAuthenticationToken tokenObj = new UsernamePasswordAuthenticationToken(username, null, authorities);
            // 5. 将组装的token对象存Asecurity上下义

            SecurityContextHolder.getContext().setAuthentication(tokenObj);

            // 放行登录请求不需要携带token
            doFilter(request,response,filterChain);
        }catch (AuthenticationException e){
            // 验证失败
            loginFailureHandler.onAuthenticationFailure(request,response,e);
        }

    }
}
