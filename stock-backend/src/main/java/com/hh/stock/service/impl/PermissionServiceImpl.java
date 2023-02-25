package com.hh.stock.service.impl;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.hh.stock.domain.sys.Permission;
import com.hh.stock.mapper.sys.PermissionMapper;
import com.hh.stock.service.PermissionService;
import com.hh.stock.vo.resp.PermissionRespNodeVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;


/**
 * @author : hh
 * @date : 2023/1/30 21:35
 * @description : 针对表【sys_permission】的数据库操作Service实现
 */
@Service("permissionService")
@Transactional
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {


    /**
     * 根据用户ID 查询权限菜单列表
     *
     */
    @Override
    public List<Permission> findPermissionListById(String userId) {
        return baseMapper.findPermissionListById(userId);
    }

    /**
     * 构造权限树
     * @param permissions
     * @param pid
     * @param isOnlyMenuType
     * @return
     */
    @Override
    public List<PermissionRespNodeVo> getTree(List<Permission> permissions, String pid, boolean isOnlyMenuType) {
        ArrayList<PermissionRespNodeVo> list = Lists.newArrayList();
        if(CollectionUtils.isEmpty(permissions)){
            return list;
        }
        for(Permission permission : permissions){
            if(permission.getPid().equals(pid)){
                if(permission.getType().intValue() != 3 || !isOnlyMenuType){
                    PermissionRespNodeVo respNodeVo = new PermissionRespNodeVo();
                    respNodeVo.setId(permission.getId());
                    respNodeVo.setTitle(permission.getTitle());
                    respNodeVo.setIcon(permission.getIcon());
                    respNodeVo.setPath(permission.getUrl());
                    respNodeVo.setName(permission.getName());
                    respNodeVo.setChildren(getTree(permissions, permission.getId(), isOnlyMenuType));
                    list.add(respNodeVo);
                }
            }
        }

        return list;
    }

}
