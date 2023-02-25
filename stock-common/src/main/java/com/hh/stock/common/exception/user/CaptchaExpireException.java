package com.hh.stock.common.exception.user;

/**
 * @author : hh
 * @date : 2023/2/13 12:29
 * @description : 验证码失效异常类
 */

public class CaptchaExpireException extends UserException
{
    private static final long serialVersionUID = 1L;

    public CaptchaExpireException()
    {
        super("user.jcaptcha.expire", null);
    }
}