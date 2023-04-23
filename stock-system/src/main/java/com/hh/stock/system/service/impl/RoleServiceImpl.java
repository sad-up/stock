package com.hh.stock.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hh.stock.common.constant.UserConstants;
import com.hh.stock.common.core.domain.AjaxResult;
import com.hh.stock.common.core.domain.entity.Role;
import com.hh.stock.common.core.domain.entity.User;
import com.hh.stock.common.core.domain.stockvo.PageResult;
import com.hh.stock.common.exception.ServiceException;
import com.hh.stock.common.utils.SecurityUtils;
import com.hh.stock.common.utils.StringUtils;
import com.hh.stock.common.utils.spring.SpringUtils;
import com.hh.stock.common.utils.uuid.IdWorker;
import com.hh.stock.system.domain.RolePermission;
import com.hh.stock.system.domain.UserRole;
import com.hh.stock.system.mapper.RolePermissionMapper;
import com.hh.stock.system.mapper.UserRoleMapper;
import com.hh.stock.system.service.RoleService;
import com.hh.stock.system.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
* @author Huanghe
* @description 针对表【sys_role(角色表)】的数据库操作Service实现
* @createDate 2023-02-11 18:35:22
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    public Set<String> findRolePermissionByUserId(String userId) {
        List<Role> perms = roleMapper.findRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for(Role perm: perms){
            if(StringUtils.isNotNull(perms)){
                permsSet.addAll(Arrays.asList(perm.getName().trim().split(".")));
            }
        }
        return permsSet;
    }

    /**
     * 获取权限列表
     * @param
     * @return
     */
    @Override
    public AjaxResult getRoleList(HttpServletRequest request, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        String roleName = request.getParameter("roleName");
        String status = request.getParameter("status");
        String beginTime = request.getParameter("params[beginTime]");
        String endTime = request.getParameter("params[endTime]");
        List<Role> pages = roleMapper.findRoleList(roleName, status, beginTime, endTime);
        if (CollectionUtils.isEmpty(pages)) {
            return AjaxResult.error(AjaxResult.ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        // 3.组装PageInfo对象,封装了一切分页信息
        PageInfo<Role> pageInfo = new PageInfo<>(pages);
        // 4. 转化成自定义得分页对象
        PageResult<Role> pageResult = new PageResult<>(pageInfo);
        return AjaxResult.success(pageResult);
    }


    /**
     * 批量删除角色信息
     *
     * @param roleIds 需要删除的角色ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteRoleByIds(String[] roleIds) {
        for (String roleId : roleIds)
        {
            checkRoleAllowed(new Role(roleId));
            checkRoleDataScope(roleId);
            Role role = selectRoleById(roleId);
            if (countUserRoleByRoleId(roleId) > 0)
            {
                throw new ServiceException(String.format("%1$s已分配,不能删除", role.getName()));
            }
        }
        // 删除角色与菜单关联
        rolePermissionMapper.deleteRoleMenu(roleIds);
        return roleMapper.deleteRoleByIds(roleIds);
    }

    /**
     * 校验角色是否允许操作
     *
     *  role 角色信息
     */
    @Override
    public void checkRoleAllowed(Role role) {
        if (StringUtils.isNotNull(role.getId()) && role.isAdmin())
        {
            throw new ServiceException("不允许操作超级管理员角色");
        }
    }

    /**
     * 校验角色是否有数据权限
     *
     * @param roleId 角色id
     */
    @Override
    public void checkRoleDataScope(String roleId) {
        if (!User.isAdmin(SecurityUtils.getUserId()))
        {
            Role role = new Role();
            role.setId(roleId);
            List<Role> roles = SpringUtils.getAopProxy(this).selectRoleList(role);
            if (StringUtils.isEmpty(roles))
            {
                throw new ServiceException("没有权限访问角色数据！");
            }
        }
    }

    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    @Override
    public List<Role> selectRoleList(Role role) {
        return roleMapper.selectRoleList(role);
    }
    /**
     * 校验角色名称是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public String checkRoleNameUnique(Role role) {
        String roleId = StringUtils.isNull(role.getId()) ? "" : role.getId();
        Role info = roleMapper.checkRoleNameUnique(role.getName());
        if (StringUtils.isNotNull(info) && !info.getId().equals(roleId))
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertRole(Role role) {
        role.setId(idWorker.nextId()+"");
        // 新增角色信息
        roleMapper.insertRole(role);
        return insertRoleMenu(role);
    }

    /**
     * 新增角色菜单信息
     *
     * @param role 角色对象
     */
    public int insertRoleMenu(Role role)
    {
        int rows = 1;
        // 新增用户与角色管理
        List<RolePermission> list = new ArrayList<RolePermission>();
        for (String menuId : role.getMenuIds())
        {
            RolePermission rm = new RolePermission();
            rm.setId(idWorker.nextId()+"");
            rm.setRoleId(role.getId());
            rm.setPermissionId(menuId);
            list.add(rm);
        }
        if (list.size() > 0)
        {


            rows = rolePermissionMapper.batchRoleMenu(list);
        }
        return rows;
    }

    /**
     * 修改角色状态
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public int updateRoleStatus(Role role)
    {
        return roleMapper.updateRole(role);
    }


    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    @Override
    public Role selectRoleById(String roleId)
    {
        return roleMapper.selectRoleById(roleId);
    }

    /**
     * 修改保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateRole(Role role) {
        // 修改角色信息
        roleMapper.updateRole(role);
        // 删除角色与菜单关联
        rolePermissionMapper.deleteRoleMenuByRoleId(role.getRoleId());
        return insertRoleMenu(role);
    }

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    @Override
    public List<Role> selectRoleAll()
    {
        return SpringUtils.getAopProxy(this).selectRoleList(new Role());
    }


    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    public List<Role> selectRolesByUserId(String userId)
    {
        List<Role> userRoles = roleMapper.selectRolePermissionByUserId(userId);
        List<Role> roles = selectRoleAll();
        for (Role role : roles)
        {
            for (Role userRole : userRoles)
            {
                if (role.getRoleId().equals(userRole.getRoleId()))
                {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }


    /**
     * 取消授权用户角色
     *
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    @Override
    public int deleteAuthUser(UserRole userRole)
    {
        return userRoleMapper.deleteUserRoleInfo(userRole);
    }


    /**
     * 批量取消授权用户角色
     *
     * @param roleId 角色ID
     * @param userIds 需要取消授权的用户数据ID
     * @return 结果
     */
    @Override
    public int deleteAuthUsers(String roleId, String[] userIds)
    {
        return userRoleMapper.deleteUserRoleInfos(roleId, userIds);
    }


    /**
     * 批量选择授权用户角色
     *
     * @param roleId 角色ID
     * @param userIds 需要授权的用户数据ID
     * @return 结果
     */
    @Override
    public int insertAuthUsers(String roleId, String[] userIds)
    {
        // 新增用户与角色管理
        List<UserRole> list = new ArrayList<UserRole>();
        for (String userId : userIds)
        {
            UserRole ur = new UserRole();
            ur.setId(idWorker.nextId()+"");
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        return userRoleMapper.batchUserRole(list);
    }

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public int countUserRoleByRoleId(String roleId)
    {
        return userRoleMapper.countUserRoleByRoleId(roleId);
    }
}




