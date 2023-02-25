package com.hh.stock.mapper.sys;

import com.hh.stock.domain.sys.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author Huanghe
* @description 针对表【sys_role(角色表)】的数据库操作Mapper
* @createDate 2023-01-31 09:27:02
* @Entity com.hh.stock.domain.sys.Role
*/
@Mapper
public interface RoleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    /**
     * 根据用户id查用户角色
     * @param id
     * @return
     */
    List<Role> findRoleByUserId(String id);
}
