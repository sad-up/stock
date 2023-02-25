package com.hh.stock.config.security.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.base.Strings;
import com.hh.stock.domain.sys.Permission;
import com.hh.stock.domain.sys.Role;
import com.hh.stock.domain.sys.User;
import com.hh.stock.mapper.sys.PermissionMapper;
import com.hh.stock.mapper.sys.RoleMapper;
import com.hh.stock.mapper.sys.UserMapper;
import com.hh.stock.service.PermissionService;
import com.hh.stock.vo.resp.PermissionRespNodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.SystemUtils.getUserName;

/**
 * @author : hh
 * @date : 2023/2/2 18:29
 * @description : 自定义加载用户信息的服务bean
 */
@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionService permissionService;

    /**
     * 用户认证时会将用户名注入到该方法中，根据用户名查询用户信息，并封装到UserDetail下
     * @param username the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1.根据用户名查询用户信息
        User user = userMapper.findUserInfoByUserName(username);

        if (user == null) {
            throw  new UsernameNotFoundException("用户不存在!");
        }
        //获取权限集合 根据用户ID查询用户拥有的权限集合
        List<Permission> permissionList = permissionMapper.findPermissionListById(user.getId());
        List<String> permsNameList = permissionList.stream()
                .filter(item -> !Strings.isNullOrEmpty(item.getPerms()))
                .map(item -> item.getPerms())
                .filter(StrUtil::isNotBlank)
                .collect(Collectors.toList());
        //获取角色合计 基于角色鉴权注解需要将角色前追加ROLE_
        List<Role> roleList = roleMapper.findRoleByUserId(user.getId());
        //将用户拥有的角色名称追加ROLE_，底层也会作为一种权限标志与权限一块使用
        List<String> roleNameList = roleList.stream()
                .filter(item ->!Strings.isNullOrEmpty(item.getName()))
                .map(item -> "ROLE_" + item.getName())
                .collect(Collectors.toList());

        // 将用户权限和角色信息封装到list
        List<String> auths = new ArrayList<>();
        auths.addAll(permsNameList);
        auths.addAll(roleNameList);

        // 转化为数组
        String[] perms = auths.toArray(new String[auths.size()]);

        // 转化为数组 给springSecurity -> 转为权限对象的集合
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(perms);
        user.setAuthorities(authorityList);
        // 权限树结构，给前端响应
        List<PermissionRespNodeVo> treeNodeVo = permissionService.getTree(permissionList,"0",true);
        user.setMenus(treeNodeVo);

        //按钮权限集合，给前端响应
        List<String> authBtnPerms = null;
        if(!CollectionUtils.isEmpty(permissionList)){
            authBtnPerms = permissionList.stream()
                    .filter(per -> !Strings.isNullOrEmpty(per.getCode()) && per.getType() ==3 )
                    .map(per -> per.getCode()).collect(Collectors.toList());
        }
        user.setPermissions(authBtnPerms);
        return user;
    }
}
