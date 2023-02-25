package com.hh.stock.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hh.stock.common.constant.Constants;
import com.hh.stock.common.constant.UserConstants;
import com.hh.stock.common.core.domain.TreeSelect;
import com.hh.stock.common.utils.SecurityUtils;
import com.hh.stock.common.utils.StringUtils;
import com.hh.stock.common.core.domain.entity.Permission;
import com.hh.stock.system.domain.vo.MetaVo;
import com.hh.stock.system.domain.vo.RouterVo;
import com.hh.stock.system.service.PermissionService;
import com.hh.stock.system.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
* @author Huanghe
* @description 针对表【sys_permission(权限表（菜单）)】的数据库操作Service实现
* @createDate 2023-02-11 18:35:22
*/
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
    implements PermissionService{

    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> findMenuPermsByUserId(String userId) {
        List<String> perms = permissionMapper.findMenuPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for(String perm: perms){
            if(StringUtils.isNotEmpty(perms)){
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }


    /**
     * 根据角色ID查询权限
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    @Override
    public Set<String> findMenuPermsByRoleId(String roleId) {
        List<String> perms = permissionMapper.findMenuPermsByRoleId(roleId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms)
        {
            if (StringUtils.isNotEmpty(perm))
            {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户名称
     * @return 菜单列表
     */
    @Override
    public List<Permission> findMenuTreeByUserId(String userId) {
        List<Permission> menus = null;
        //if(SecurityUtils.isAdmin(userId)){
        //    menus = permissionMapper.findMenuTreeAll(userId);
        //}
        //else{
            menus = permissionMapper.findMenuTreeByUserId(userId);
        //}
        return getChildPerms(menus, "0");
    }



    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    @Override
    public List<RouterVo> buildMenus(List<Permission> menus)
    {
        List<RouterVo> routers = new LinkedList<RouterVo>();
        for (Permission menu : menus)
        {
            RouterVo router = new RouterVo();
            router.setHidden(false);
            router.setName(getRouteName(menu));
            router.setPath(getRouterPath(menu));
            router.setComponent(getComponent(menu));
//            router.setQuery(menu.getQuery());
            router.setMeta(new MetaVo(menu.getTitle(), menu.getIcon()));
            List<Permission> cMenus = menu.getChildren();
            if (!cMenus.isEmpty() && cMenus.size() > 0 && UserConstants.TYPE_DIR.equals(menu.getType()))
            {
               router.setAlwaysShow(true);
               router.setRedirect("noRedirect");
               router.setChildren(buildMenus(cMenus));
            }
            else if (isMenuFrame(menu))
            {
               router.setMeta(null);
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                children.setPath(menu.getUrl());
                children.setComponent(menu.getComponent());
                children.setName(StringUtils.capitalize(menu.getUrl()));
                children.setMeta(new MetaVo(menu.getTitle(), menu.getIcon(), true, menu.getUrl()));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            else if (menu.getPid().equals("0") && isInnerLink(menu))
            {
                router.setMeta(new MetaVo(menu.getTitle(), menu.getIcon()));
                router.setPath("/");
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                String routerPath = innerLinkReplaceEach(menu.getUrl());
                children.setPath(routerPath);
                children.setComponent(UserConstants.INNER_LINK);
                children.setName(StringUtils.capitalize(routerPath));
                children.setMeta(new MetaVo(menu.getTitle(), menu.getIcon(), menu.getUrl()));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }



    /**
     * 获取路由名称
     *
     * @param menu 菜单信息
     * @return 路由名称
     */
    public String getRouteName(Permission menu)
    {
        String routerName = StringUtils.capitalize(menu.getUrl());
        // 非外链并且是一级目录（类型为目录）
        if (isMenuFrame(menu))
        {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(Permission menu)
    {
        String routerPath = menu.getUrl();
        // 内链打开外网方式
        if (menu.getPid().equals('0') && isInnerLink(menu))
        {
            routerPath = innerLinkReplaceEach(routerPath);
        }
        // 非外链并且是一级目录（类型为目录）
        if ("0".equals(menu.getPid()) && UserConstants.TYPE_DIR.equals(menu.getType())
                && UserConstants.NO_FRAME.equals(menu.getFrame()))
        {
            routerPath = "/" + menu.getUrl();
        }
        // 非外链并且是一级目录（类型为菜单）
        else if (isMenuFrame(menu))
        {
            routerPath = "/";
        }
        return routerPath;
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    public String getComponent(Permission menu)
    {
        String component = UserConstants.LAYOUT;
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMenuFrame(menu))
        {
            component = menu.getComponent();
        }
        else if (StringUtils.isEmpty(menu.getComponent()) && !menu.getPid().equals("0") && isInnerLink(menu))
        {
            component = UserConstants.INNER_LINK;
        }
        else if (StringUtils.isEmpty(menu.getComponent()) && isParentView(menu))
        {
            component = UserConstants.PARENT_VIEW;
        }
        return component;
    }
    /**
     * 是否为内链组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isInnerLink(Permission menu)
    {
        return "0".equals(UserConstants.NO_FRAME) && StringUtils.ishttp(menu.getUrl());
    }
    /**
     * 是否为parent_view组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isParentView(Permission menu)
    {
        return !menu.getPid().equals("0")  && UserConstants.TYPE_DIR.equals(menu.getType());
    }

    /**
     * 构建前端所需要树结构
     *
     * @param menus 菜单列表
     * @return 树结构列表
     */
//    @Override
//    public List<Permission> buildMenuTree(List<Permission> menus)
//    {
//        List<Permission> returnList = new ArrayList<Permission>();
//        List<String> tempList = menus.stream().map(Permission::getId).collect(Collectors.toList());
//        for (Iterator<Permission> iterator = menus.iterator(); iterator.hasNext();)
//        {
//            Permission menu = (Permission) iterator.next();
//            // 如果是顶级节点, 遍历该父节点的所有子节点
//            if (!tempList.contains(menu.getPid()))
//            {
//                recursionFn(menus, menu);
//                returnList.add(menu);
//            }
//        }
//        if (returnList.isEmpty())
//        {
//            returnList = menus;
//        }
//        return returnList;
//    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
//    @Override
//    public List<TreeSelect> buildMenuTreeSelect(List<Permission> menus)
//    {
//        List<Permission> menuTrees = buildMenuTree(menus);
//        return menuTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
//    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list 分类表
     * @param pid 传入的父节点ID
     * @return String
     */
    public List<Permission> getChildPerms(List<Permission> list, String pid){
        List<Permission> menusList = new ArrayList<Permission>();
        for(Iterator<Permission> iterator = list.iterator();iterator.hasNext();){
            Permission n = iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if(n.getPid().equals(pid)){
                recursionFn(list, n);
                menusList.add(n);
            }
        }
        return menusList;
    }

    /**
     * 递归列表
     *
     * @param list 分类表
     * @param p 子节点
     */
    private void recursionFn(List<Permission> list, Permission p)
    {
        // 得到子节点列表
        List<Permission> childList = getChildList(list, p);
        p.setChildren(childList);
        for (Permission pChild : childList)
        {
            if (hasChild(list, pChild))
            {
                recursionFn(list, pChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<Permission> getChildList(List<Permission> list, Permission p)
    {
        List<Permission> plist = new ArrayList<Permission>();
        Iterator<Permission> it = list.iterator();
        while (it.hasNext())
        {


            Permission n = (Permission) it.next();
            if (p.getId().equals(n.getPid()))
            {
                plist.add(n);
            }

        }
        return plist;
    }

    /**
     * 是否为菜单内部跳转
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isMenuFrame(Permission menu)
    {
        return menu.getPid().equals('0')  && UserConstants.TYPE_MENU.equals(menu.getType())
                && menu.getFrame().equals(UserConstants.NO_FRAME);
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<Permission> list, Permission p)
    {
        return getChildList(list, p).size() > 0;
    }

    /**
     * 内链域名特殊字符替换
     *
     * @return 替换后的内链域名
     */
    public String innerLinkReplaceEach(String path)
    {
        return StringUtils.replaceEach(path, new String[] { Constants.HTTP, Constants.HTTPS, Constants.WWW, "." },
                new String[] { "", "", "", "/" });
    }
}




