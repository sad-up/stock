package com.hh.stock.utils.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : hh
 * @date : 2023/1/24 20:51
 * @description : 封装token返回的数据信息
 */

@Data
@NoArgsConstructor
@AllArgsConstructor

public class LoginResult {
    //用户编号
    private String id;
    //状态码
    private int code;
    //token令牌
    private String token;
    //token过期时间
    private Long expireTime;


}
