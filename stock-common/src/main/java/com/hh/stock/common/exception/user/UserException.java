package com.hh.stock.common.exception.user;
import com.hh.stock.common.exception.base.BaseException;
/**
 * @author : hh
 * @date : 2023/2/13 11:59
 * @description : 用户信息异常类
 */

public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}