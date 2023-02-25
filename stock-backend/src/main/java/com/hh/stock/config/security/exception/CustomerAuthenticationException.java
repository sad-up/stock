package com.hh.stock.config.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author : hh
 * @date : 2023/1/25 21:41
 * @description : 自定义验证异常类
 */

public class CustomerAuthenticationException extends AuthenticationException {
    public CustomerAuthenticationException(String message){
        super(message);
    }
}
