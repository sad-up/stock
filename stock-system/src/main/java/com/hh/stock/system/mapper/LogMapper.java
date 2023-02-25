package com.hh.stock.system.mapper;

import com.hh.stock.system.domain.Log;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Huanghe
* @description 针对表【sys_log(系统日志)】的数据库操作Mapper
* @createDate 2023-02-11 19:14:48
* @Entity com.hh.stock.system.domain.Log
*/
@Mapper
public interface LogMapper extends BaseMapper<Log> {

}




