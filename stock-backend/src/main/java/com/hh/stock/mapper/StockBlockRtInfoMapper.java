package com.hh.stock.mapper;

import com.hh.stock.domain.StockBlockRtInfo;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Huanghe
* @description 针对表【stock_block_rt_info(股票板块详情信息表)】的数据库操作Mapper
* @createDate 2023-01-31 09:28:01
* @Entity com.hh.stock.domain.StockBlockRtInfo
*/
@Mapper
public interface StockBlockRtInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockBlockRtInfo record);

    int insertSelective(StockBlockRtInfo record);

    StockBlockRtInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockBlockRtInfo record);

    int updateByPrimaryKey(StockBlockRtInfo record);

}
