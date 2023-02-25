package com.hh.stock.domain.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 权限表（菜单）
 * @TableName sys_permission
 */
@TableName(value ="sys_permission")
@Data
public class Permission implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 菜单权限编码(前端按钮权限标识)
     */
    private String code;

    /**
     * 菜单权限名称
     */
    private String title;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 授权(如：sys:user:add)
     */
    private String perms;

    /**
     * 访问地址URL
     */
    private String url;



    /**
     * 资源请求类型
     */
    private String method;

    /**
     * name与前端vue路由name约定一致
     */
    private String name;

    /**
     * 父级菜单权限id
     */
    private String pid;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 菜单权限类型(1:目录;2:菜单;3:按钮)
     */
    private Integer type;

    /**
     * 状态1:正常 0：禁用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除(1未删除；0已删除)
     */
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 子菜单列表
     */
    @JsonInclude(JsonInclude.Include.NON_NULL) //属性值为null不进行序列化操作
    @TableField(exist = false)
    private List<Permission> children = new ArrayList<Permission>();
    /**
     * 用于前端判断是菜单、目录或按钮
     */
    @TableField(exist = false)
    private String value;
    /**
     * 是否展开
     */
    @TableField(exist = false)
    private Boolean open;
}