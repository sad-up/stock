package com.hh.stock.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hh.stock.common.core.domain.entity.Role;
import com.hh.stock.common.utils.StringUtils;
import com.hh.stock.system.service.RoleService;
import com.hh.stock.system.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
* @author Huanghe
* @description 针对表【sys_role(角色表)】的数据库操作Service实现
* @createDate 2023-02-11 18:35:22
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Autowired
    private RoleMapper roleMapper;

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
}




