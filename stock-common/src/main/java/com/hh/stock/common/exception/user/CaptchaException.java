package com.hh.stock.common.exception.user;

/**
 * @author : hh
 * @date : 2023/2/13 12:30
 * @description : 验证码错误异常类
 */

public class CaptchaException extends UserException {
    private static final long serialVersionUID = 1L;

    public CaptchaException()
    {
        super("user.jcaptcha.error", null);
    }
}

