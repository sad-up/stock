package com.hh.stock.web.controller.system;

import com.hh.stock.common.constant.UserConstants;
import com.hh.stock.common.core.controller.BaseController;
import com.hh.stock.common.core.domain.AjaxResult;
import com.hh.stock.common.core.domain.entity.Permission;
import com.hh.stock.common.utils.StringUtils;
import com.hh.stock.system.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.hh.stock.common.utils.SecurityUtils.getUserId;

/**
 * @author : hh
 * @date : 2023/4/15 0:57
 * @description : 菜单信息
 */

@RestController
@RequestMapping("/api/menu")
public class PermissionController extends BaseController
{
    @Autowired
    private PermissionService menuService;

    /**
     * 获取菜单列表
     */
    @GetMapping("/list")
    public AjaxResult list(Permission menu)
    {
        List<Permission> menus = menuService.selectMenuList(menu, getUserId());
        return success(menus);

    }

    /**
     * 根据菜单编号获取详细信息
     */
    @GetMapping(value = "/{menuId}")
    public AjaxResult getInfo(@PathVariable String menuId)
    {
        return success(menuService.selectMenuById(menuId));
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect(Permission menu)
    {
        List<Permission> menus = menuService.selectMenuList(menu, getUserId());
        return success(menuService.buildMenuTreeSelect(menus));
    }

    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public AjaxResult roleMenuTreeselect(@PathVariable("roleId") String roleId)
    {
        List<Permission> menus = menuService.selectMenuList(getUserId());
        AjaxResult ajax = AjaxResult.success();
        ajax.put("checkedKeys", menuService.selectMenuListByRoleId(roleId));
        ajax.put("menus", menuService.buildMenuTreeSelect(menus));
        return ajax;
    }

    /**
     * 新增菜单
     */

    @PostMapping
    public AjaxResult add(@Validated @RequestBody Permission menu)
    {
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu)))
        {
            return error("新增菜单'" + menu.getTitle() + "'失败，菜单名称已存在");
        }
        else if (UserConstants.YES_FRAME.equals(menu.getFrame()) && !StringUtils.ishttp(menu.getUrl()))
        {
            return error("新增菜单'" + menu.getTitle() + "'失败，地址必须以http(s)://开头");
        }
        menu.setCreateBy(getUsername());
        return toAjax(menuService.insertMenu(menu));
    }

    /**
     * 修改菜单
     */
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody Permission menu)
    {
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu)))
        {
            return error("修改菜单'" + menu.getTitle() + "'失败，菜单名称已存在");
        }
        else if (UserConstants.YES_FRAME.equals(menu.getFrame()) && !StringUtils.ishttp(menu.getUrl()))
        {
            return error("修改菜单'" + menu.getTitle() + "'失败，地址必须以http(s)://开头");
        }
        else if (menu.getId().equals(menu.getPid()))
        {
            return error("修改菜单'" + menu.getTitle() + "'失败，上级菜单不能选择自己");
        }
        menu.setUpdateBy(getUsername());
        return toAjax(menuService.updateMenu(menu));
    }

    /**
     * 删除菜单
     */
    @DeleteMapping("/{menuId}")
    public AjaxResult remove(@PathVariable("menuId") String menuId)
    {
        if (menuService.hasChildByMenuId(menuId))
        {
            return warn("存在子菜单,不允许删除");
        }
        if (menuService.checkMenuExistRole(menuId))
        {
            return warn("菜单已分配,不允许删除");
        }
        return toAjax(menuService.deleteMenuById(menuId));
    }

}
