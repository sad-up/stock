package com.hh.stock.common.core.domain.model;

/**
 * @author : hh
 * @date : 2023/2/10 16:31
 * @description : 用户登录对象
 */

public class LoginBody {

    private String username;
    private String password;
    private String code;
    private String uuid;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
