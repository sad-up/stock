package com.hh.stock.config.security.utils;

import com.hh.stock.domain.sys.Permission;
import com.hh.stock.vo.req.RouterVo;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * @author : hh
 * @date : 2023/1/26 16:28
 * @description : 生成菜单树
 */

public class MenuTree {
    /**
     * 生成路由
     *
     * @param meuList 菜单列表
     * @param pid 父级菜单
     * @return
     */

    public static  List<RouterVo> makeRouter(List<Permission> meuList, String pid){
        // 创建集合保存路由信息
        List<RouterVo> routerVoList = new ArrayList<RouterVo>();
        // 判断菜单列表是否为空，如果不为空则使用菜单列表，否则创建集合对象
        Optional.ofNullable(meuList).orElse(new ArrayList<Permission>())
                // 筛选不为空的菜单以及菜单父ID相同的数据
                .stream().filter(item -> item != null && item.getPid().equals(pid) )
                .forEach(item -> {
                    //创建路由信息对象
                    RouterVo routerVo = new RouterVo();
                    routerVo.setName(item.getName()); // 路由名称
                    routerVo.setPath(item.getUrl()); // 路由地址
                    // 判断当前菜单是否为一级菜单
                    if(item.getPid().equals(0L)){
                        routerVo.setComponent("Layout"); // 一级菜单组件
                        routerVo.setAlwaysShow(true);
                    }else{
                        routerVo.setComponent(item.getUrl()); // 具体某一个组件
                        routerVo.setAlwaysShow(false); // 折叠路由
                    }
                    // 设置Meta信息
                    routerVo.setMeta(routerVo.new Meta(item.getTitle(), item.getIcon(), item.getPerms().split(" ")));
                    // 递归生成路由
                    List<RouterVo> children =  makeRouter(meuList, item.getId()); //子菜单
                    routerVo.setChildren(children); // 设置子路由到路由对象中
                    //将路由信息添加到集合中
                    routerVoList.add(routerVo);
        });
        return routerVoList;
    }


    /**
     * 生成菜单树
     *
     * @param meuList
     * @param pid
     * @return
     */
    private static List<Permission> makeMenuTree(List<Permission> meuList, String pid){
        //创建集合保存菜单数据
        List<Permission> permissionList = new ArrayList<Permission>();
        // 判断菜单列表是否为空，如果不为空则使用菜单列表，否则创建集合对象
        Optional.ofNullable(meuList).orElse(new ArrayList<Permission>())
                .stream().filter(item -> item != null && item.getPid().equals(pid))
                .forEach(item ->{
                    //创建菜单信息对象
                    Permission permission = new Permission();
                    // 将原有的属性复制给菜单对象
                    BeanUtils.copyProperties(item, permission);
                    // 获取每个itme下面的子菜单，递归生成菜单树
                    List<Permission> children = makeMenuTree(meuList, item.getId());
                    // 设置子菜单
                    permission.setChildren(children);
                    // 将子菜单对象添加到集合
                    permissionList.add(permission);
                });
        // 返回子菜单
        return permissionList;
    }

}

