package com.hh.stock.framework.security.context;

import org.springframework.security.core.Authentication;

/**
 * @author : hh
 * @date : 2023/2/13 15:05
 * @description : some description
 */

public class AuthenticationContextHolder {
    private static final ThreadLocal<Authentication> contextHolder = new ThreadLocal<>();

    public static Authentication getContext()
    {
        return contextHolder.get();
    }

    public static void setContext(Authentication context)
    {
        contextHolder.set(context);
    }

    public static void clearContext()
    {
        contextHolder.remove();
    }
}