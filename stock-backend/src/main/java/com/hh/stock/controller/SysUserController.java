package com.hh.stock.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hh.stock.config.redis.RedisService;
import com.hh.stock.domain.UserInfo;
import com.hh.stock.domain.sys.Permission;
import com.hh.stock.domain.sys.User;
import com.hh.stock.config.security.utils.JwtUtils;
import com.hh.stock.config.security.utils.MenuTree;
import com.hh.stock.vo.req.RouterVo;
import com.hh.stock.vo.req.TokenVo;
import com.hh.stock.vo.resp.R;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @author : hh
 * @date : 2023/1/26 13:13
 * @description : some description
 */

@RestController
@RequestMapping("/api/sysUser")
public class SysUserController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private JwtUtils jwtUtils;
    /**
     * 刷新token
     *
     * @param request
     * @return
     */
    @PostMapping("/refreshToken")
    public R refreshToken(HttpServletRequest request) {
        //从header中获取前端提交的token
        String token = request.getHeader("token");
        //如果header中没有token，则从参数中获取
        if (ObjectUtils.isEmpty(token)) {
            token = request.getParameter("token");
        }
        //从Spring Security上下文获取用户信息
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        //获取身份信息
        UserDetails details = (UserDetails) authentication.getPrincipal();
        //重新生成token
        String reToken = "";
        //验证原来的token是否合法
        if (jwtUtils.validateToken(token, details)) {
            //生成新的token
            reToken = jwtUtils.refreshToken(token);
        }
        //获取本次token的到期时间，交给前端做判断
        long expireTime = Jwts.parser().setSigningKey(jwtUtils.getSecret())
                .parseClaimsJws(reToken.replace("jwt_", ""))
                .getBody().getExpiration().getTime();
        //清除原来的token信息
        String oldTokenKey = "token_" + token;
        redisService.del(oldTokenKey);
        //存储新的token
        String newTokenKey = "token_" + reToken;
        redisService.set(newTokenKey, reToken, jwtUtils.getExpiration() / 1000);
        //创建TokenVo对象
        TokenVo tokenVo = new TokenVo(expireTime, reToken);
        //返回数据
        return R.ok("token生成成功",tokenVo);

        }


    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/getUserInfo")
    public R getUserInfo(){
        //从Spring Security上下文获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //判断身份信息后authentication是否为空
        if(authentication == null){
            return R.error("用户信息查询失败");
        }
        // 获取用户信息
        User user = (User) authentication.getPrincipal();
        // 获取该用户所有的权限信息
        List<Permission> permissionList = user.getPermissionList();
        // 获取权限编码
        Object[] roles = permissionList.stream().filter(Objects::nonNull).map(Permission::getPerms).filter(StrUtil::isNotBlank).toArray();

        //创建用户信息
        UserInfo userInfo = new UserInfo(user.getId(),user.getNickName(),user.getAvatar(),null,roles);
        // 返回数据
        return R.ok("用户信息查询成功",userInfo);
    }





    /**
     * 获取菜单数据
     *
     * @return
     */
    @GetMapping("/getMenuList")
    public R getMenuList() {
        //从Spring Security上下文获取用户信息
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        //获取用户信息
        User user = (User) authentication.getPrincipal();
        //获取相应的权限
        List<Permission> permissionList = user.getPermissionList();
        //筛选目录和菜单
        List<Permission> collect = permissionList.stream()
                .filter(item -> item != null && item.getType() != 3)
                .collect(Collectors.toList());
        //生成路由数据
        List<RouterVo> routerVoList = MenuTree.makeRouter(collect, "0");
        //返回数据
        return R.ok("菜单数据获取成功",routerVoList);
    }
}
