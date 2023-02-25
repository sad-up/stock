package com.hh.stock.common.exception;

/**
 * @author : hh
 * @date : 2023/2/10 14:54
 * @description :  工具类异常
 */
public class UtilException extends RuntimeException
{
    private static final long serialVersionUID = 8247610319171014183L;

    public UtilException(Throwable e)
    {
        super(e.getMessage(), e);
    }

    public UtilException(String message)
    {
        super(message);
    }

    public UtilException(String message, Throwable throwable)
    {
        super(message, throwable);
    }
}
