package com.hh.stock.system.mapper;

import com.hh.stock.system.domain.StockBlockRtInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Huanghe
* @description 针对表【stock_block_rt_info(股票板块详情信息表)】的数据库操作Mapper
* @createDate 2023-02-11 18:35:21
* @Entity com.hh.stock.domain.StockBlockRtInfo
*/
@Mapper
public interface StockBlockRtInfoMapper extends BaseMapper<StockBlockRtInfo> {

}




