package com.hh.stock.domain.sys;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.util.List;

import com.hh.stock.vo.resp.PermissionRespNodeVo;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户表
 * @TableName sys_user
 */
@TableName(value ="sys_user")
@Data
public class User implements UserDetails {
    /**
     * 用户id
     */
    @TableId
    private String id;

    /**
     * 账户
     */
    private String username;

    /**
     * 用户密码密文
     */
    private String password;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 真实名称
     */
    private String realName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 邮箱(唯一)
     */
    private String email;

    /**
     * 用户头像	
     */
    private String avatar;

    /**
     * 账户状态(1.正常 2.锁定 )
     */
    private Integer status;

    /**
     * 性别(1.男 2.女)
     */
    private Integer sex;

    /**
     * 是否删除(1未删除；0已删除)
     */
    private Integer deleted;

    /**
     * 创建人
     */
    private String createId;

    /**
     * 更新人
     */
    private String updateId;

    /**
     * 创建来源(1.web 2.android 3.ios )
     */
    private Integer createWhere;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 用户权限列表
     */
    @TableField(exist = false)
    private List<Permission> permissionList;

    /**
     * 权限集合
     */
    @TableField(exist = false)
    private List<GrantedAuthority> Authorities;

    /**
     * true:账户没有过期
     */
    private boolean isAccountNonExpired = true;

    /**
     * true: 账户没有被锁定
     */
    private boolean isAccountNonLocked = true;

    /**
     * true: 密码没有过期
     */
    private boolean isCredentialsNonExpired = true;

    /**
     * true: 账户有效
     */
    private boolean isEnabled = true;

    /**
     * 前端对应用户菜单(不包含按钮)
     */
    @TableField(exist = false)
    private List<PermissionRespNodeVo> menus;

    /**
     * 用户拥有的按钮权限
     */
    @TableField(exist = false)
    private List<String> permissions;
}