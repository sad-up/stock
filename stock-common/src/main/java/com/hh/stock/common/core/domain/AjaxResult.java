package com.hh.stock.common.core.domain;

import com.hh.stock.common.constant.HttpStatus;
import com.hh.stock.common.utils.StringUtils;

import java.util.HashMap;

/**
 * @author : hh
 * @date : 2023/2/10 11:40
 * @description : 操作消息提醒
 */

public class AjaxResult extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    /** 状态码 */
    public static final String CODE_TAG = "code";

    /** 返回内容 */
    public static final String MSG_TAG = "msg";

    /** 数据对象 */
    public static final String DATA_TAG = "data";

    /**
     * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
     */
    public AjaxResult()
    {
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg 返回内容
     */
    public AjaxResult(int code, String msg)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg 返回内容
     * @param data 数据对象
     */
    public AjaxResult(int code, String msg, Object data)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (StringUtils.isNotNull(data))
        {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static AjaxResult success()
    {
        return AjaxResult.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static AjaxResult success(Object data)
    {
        return AjaxResult.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static AjaxResult success(String msg)
    {
        return AjaxResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static AjaxResult success(String msg, Object data)
    {
        return new AjaxResult(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回警告消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static AjaxResult warn(String msg)
    {
        return AjaxResult.warn(msg, null);
    }

    /**
     * 返回警告消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static AjaxResult warn(String msg, Object data)
    {
        return new AjaxResult(HttpStatus.WARN, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return 错误消息
     */
    public static AjaxResult error()
    {
        return AjaxResult.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 错误消息
     */
    public static AjaxResult error(String msg)
    {
        return AjaxResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 错误消息
     */
    public static AjaxResult error(String msg, Object data)
    {
        return new AjaxResult(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg 返回内容
     * @return 错误消息
     */
    public static AjaxResult error(int code, String msg)
    {
        return new AjaxResult(code, msg, null);
    }

    /**
     * 方便链式调用
     *
     * @param key 键
     * @param value 值
     * @return 数据对象
     */
    @Override
    public AjaxResult put(String key, Object value)
    {
        super.put(key, value);
        return this;
    }

    public enum ResponseCode{
        ERROR(0,"操作失败"),
        SUCCESS(200,"操作成功"),
        DATA_ERROR(0,"参数异常"),
        NO_LOGIN(600,"匿名用户无权限访问"),
        NO_AUTH(700,"无权限 访问,请联系管理员！"),

        ORDER_IN_ERROR(800,"资金不足"),
        ORDER_OUT_ERROR(801,"股票不足"),

        DATE_WEEKEND_ERROR(802,"周末未开市，暂停交易"),

        DATE_BEFORE_ERROR(803,"暂未开市，请稍等"),

        DATE_AFTER_ERROR(803,"交易时间已结束哦"),

        NO_RESPONSE_DATA(0,"无响应数据"),
        SYSTEM_VERIFY_CODE_NOT_EMPTY(0,"验证码不能为空"),
        SYSTEM_VERIFY_CODE_ERROR(0,"验证码错误"),
        SYSTEM_USERNAME_NOT_EMPTY(0,"账号不能为空"),
        SYSTEM_USERNAME_NOT_EXISTS(0,"账号不存在"),
        SYSTEM_USERNAME_EXPIRED(0,"账户过期"),
        SYSTEM_USERNAME_LOCKED(0,"账户被锁"),
        SYSTEM_USERNAME_DISABLED(0,"账户被禁用"),
        SYSTEM_PASSWORD_ERROR(0,"账号或密码错误"),
        SYSTEM_PASSWORD_EXPIRED(0,"密码过期"),
        SYSTEM_USERNAME_OFFLINE(0,"已下线，请重新登录"),
        SYSTEM_ERROR(0,"系统异常请稍后再试"),
        ACCOUNT_EXISTS_ERROR(0,"该账号已存在"),
        TOKEN_ERROR(2,"用户未登录，请先登录"),
        NOT_PERMISSION(3,"没有权限访问该资源"),
        TOKEN_NOT_NULL(-1,"token 不能为空"),
        TOKEN_NO_AVAIL(-1,"token无效或过期"),
        TOKEN_PAST_DUE(-1,"登录失效,请重新登录"),
        TOKEN_EXISTS(-1,"账号异地登录，你已被迫退出"),
        OPERATION_MENU_PERMISSION_CATALOG_ERROR(0,"操作后的菜单类型是目录，所属菜单必须为默认顶级菜单或者目录"),
        OPERATION_MENU_PERMISSION_MENU_ERROR(0,"操作后的菜单类型是菜单，所属菜单必须为目录类型"),
        OPERATION_MENU_PERMISSION_BTN_ERROR(0,"操作后的菜单类型是按钮，所属菜单必须为菜单类型"),
        OPERATION_MENU_PERMISSION_URL_NOT_NULL(0,"菜单权限的url不能为空"),
        OPERATION_MENU_PERMISSION_URL_PERMS_NULL(0,"菜单权限的标识符不能为空"),
        OPERATION_MENU_PERMISSION_URL_METHOD_NULL(0,"菜单权限的请求方式不能为空"),
        OPERATION_MENU_PERMISSION_URL_CODE_NULL(0,"菜单权限的按钮标识不能为空"),
        OPERATION_MENU_PERMISSION_UPDATE(0,"操作的菜单权限存在子集关联不允许变更"),
        ROLE_PERMISSION_RELATION(0, "该菜单权限存在子集关联，不允许删除"),
        OLD_PASSWORD_ERROR(0,"旧密码不匹配");
        private int code;
        private String message;

        ResponseCode(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }

}
