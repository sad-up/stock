package com.hh.stock.domain.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 角色权限表
 * @TableName sys_role_permission
 */
@TableName(value ="sys_role_permission")
@Data
public class RolePermission implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 菜单权限id
     */
    private String permissionId;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}