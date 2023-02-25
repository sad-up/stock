package com.hh.stock.config.security;

import com.hh.stock.config.security.filter.JwtAuthorizationFilter;
import com.hh.stock.config.security.filter.JwtLoginAuthenticationFilter;
import com.hh.stock.config.security.handler.AnonymousAuthenticationHandler;
import com.hh.stock.config.security.handler.CustomerAccessDeniedHandler;
import com.hh.stock.config.security.handler.LoginFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author : hh
 * @date : 2023/2/2 18:19
 * @description : some description
 */

@Configuration
@EnableWebSecurity //开启web安全设置生效
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启前后权限校验功能，底层自动解析@Pre@Post相关得注解
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginFailureHandler loginFailureHandler;
    /**
     * 匿名用户
     * */
    @Autowired
    private AnonymousAuthenticationHandler anonymousAuthenticationHandler;

    /**
     * 登录认证用户
     * */
    @Autowired
    private CustomerAccessDeniedHandler customerAccessDeniedHandler;
    private static final String URL_MANAGEMENT[] ={
            "/**/*/css",
            "/**/*.js",
            "/api/login",
            "/Togout",
            "/api/captchaImage",
            "/password",
            "/image/**",
            "/api/sysUser/*"
    };
    /**
     * 拦截规则
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().permitAll() //设置认证form表单的方式认证

                .failureHandler(loginFailureHandler)
                .and()
                .logout().logoutUrl("/api/logout").invalidateHttpSession(true)
                .and()
                // 允许跨域共享
                .csrf().disable().cors()
                .and()
                // 开启允许iframe嵌套
                .headers().frameOptions().disable().cacheControl().disable()
                .and()
                // 禁用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 授权策略
                // 拦截规则配置
                .authorizeRequests() //设置权限规则，开启资源请求得权限校验（无论是基于配置还是注解）
                .antMatchers(URL_MANAGEMENT).permitAll() //白名单  放行
                .anyRequest().authenticated() //其他一律请求都需要进行身份认证
                .and()
                .addFilterBefore(jwtLoginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                // 设置jwtAuthorizationFilter过滤器优先级要在自定义的登录过滤器之前处理
                .addFilterBefore(jwtAuthorizationFilter(), JwtLoginAuthenticationFilter.class)
                // 异常处理
                .exceptionHandling()
                .authenticationEntryPoint(anonymousAuthenticationHandler) //匿名无权限访问
                .accessDeniedHandler(customerAccessDeniedHandler) ;//认证用户无权限访问
    }

    /**
     * 自定义认证过滤器的bean
     * @return
     * @throws Exception
     */
    @Bean
    public JwtLoginAuthenticationFilter jwtLoginAuthenticationFilter() throws Exception {
        // 指定登录路径
        JwtLoginAuthenticationFilter loginFilter = new JwtLoginAuthenticationFilter("/api/login");
        // 注册认证管理器
        loginFilter.setAuthenticationManager(this.authenticationManagerBean());
        return loginFilter;
    }

    /**
     * 配置授权过滤器的bean
     * @return
     * @throws Exception
     */
    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(){
        JwtAuthorizationFilter authorizationFilter = new JwtAuthorizationFilter();
        return authorizationFilter;
    }
}
