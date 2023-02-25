package com.hh.stock.common.utils;

/**
 * @author : hh
 * @date : 2023/2/10 18:10
 * @description : 处理并记录日志文件
 */

public class LogUtils
{
    public static String getBlock(Object msg)
    {
        if (msg == null)
        {
            msg = "";
        }
        return "[" + msg.toString() + "]";
    }
}