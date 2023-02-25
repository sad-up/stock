package com.hh.stock.vo.req;

import lombok.Data;

/**
 * @author : hh
 * @date : 2023/1/27 13:01
 * @description : 用户登录请求Vo
 */
@Data
public class LoginReqVo {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 前端发送得验证码
     */
    private String code;

    /**
     * 前端发送的sessionId
     */
    private String uuid;

    /**
     *
     */
    private boolean remember;
}
