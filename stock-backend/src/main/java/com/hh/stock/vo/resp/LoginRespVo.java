package com.hh.stock.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author : hh
 * @date : 2023/1/27 13:09
 * @description : some description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRespVo {
    /**
     * 用户ID
     */
    private String id;
    /**
     * 电话
     */
    private String phone;
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 权限树（仅显示菜单，不显示加载按钮信息）
     */
    private List<PermissionRespNodeVo> menus;

    /**
     * 按钮权限集合
     */
    private List<String> permissions;

    /**
     * 认证成功后的JwtToken
     */
    private String token;
}
