package com.hh.stock.mapper.sys;

import com.hh.stock.domain.sys.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Huanghe
* @description 针对表【sys_user_role(用户角色表)】的数据库操作Mapper
* @createDate 2023-01-31 09:27:03
* @Entity com.hh.stock.domain.sys.UserRole
*/
@Mapper
public interface UserRoleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);

}
