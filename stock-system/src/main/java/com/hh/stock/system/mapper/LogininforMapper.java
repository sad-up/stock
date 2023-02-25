package com.hh.stock.system.mapper;

import com.hh.stock.system.domain.Logininfor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Huanghe
* @description 针对表【sys_logininfor(系统访问记录)】的数据库操作Mapper
* @createDate 2023-02-11 18:35:22
* @Entity com.hh.stock.domain.Logininfor
*/
@Mapper
public interface LogininforMapper extends BaseMapper<Logininfor> {

}




