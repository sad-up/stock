package com.hh.stock.system.mapper;

import com.hh.stock.system.domain.StockRtInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Huanghe
* @description 针对表【stock_rt_info(个股详情信息表)】的数据库操作Mapper
* @createDate 2023-02-11 18:35:22
* @Entity com.hh.stock.domain.StockRtInfo
*/
@Mapper
public interface StockRtInfoMapper extends BaseMapper<StockRtInfo> {

}




