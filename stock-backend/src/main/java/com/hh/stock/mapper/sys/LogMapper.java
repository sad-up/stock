package com.hh.stock.mapper.sys;

import com.hh.stock.domain.sys.Log;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Huanghe
* @description 针对表【sys_log(系统日志)】的数据库操作Mapper
* @createDate 2023-01-31 09:27:02
* @Entity com.hh.stock.domain.sys.Log
*/
@Mapper
public interface LogMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Log record);

    int insertSelective(Log record);

    Log selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Log record);

    int updateByPrimaryKey(Log record);

}
