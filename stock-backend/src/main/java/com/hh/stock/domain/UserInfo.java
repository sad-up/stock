package com.hh.stock.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : hh
 * @date : 2023/1/26 16:11
 * @description : some description
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo implements Serializable {
    private String id;//用户ID
    private String name;//用户名称
    private String avatar;//头像
    private String introduction;//介绍
    private Object[] roles;//角色权限集合
}

