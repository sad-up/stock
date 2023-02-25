package com.hh.stock.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hh.stock.domain.sys.Permission;
import com.hh.stock.vo.resp.PermissionRespNodeVo;

import java.util.List;

/**
 * @author : hh
 * @date : 2023/1/30 21:34
 * @description : some description
 */

public interface PermissionService extends IService<Permission> {

    //根据用户ID 查询权限菜单列表
    List<Permission> findPermissionListById(String userId);

    /**
     * permisson 权限树集合
     * pid 权限父id 顶级的pid默认为0
     * isOnlyMenuType true:遍历到菜单 false:遍历到按钮
     * type: 目录1， 菜单2， 按钮3
     */
    List<PermissionRespNodeVo> getTree(List<Permission> permissions, String pid, boolean isOnlyMenuType);
}
