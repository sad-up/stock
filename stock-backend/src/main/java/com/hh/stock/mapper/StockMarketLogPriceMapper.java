package com.hh.stock.mapper;

import com.hh.stock.domain.StockMarketLogPrice;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Huanghe
* @description 针对表【stock_market_log_price(股票大盘 开盘价与前收盘价流水表)】的数据库操作Mapper
* @createDate 2023-01-31 09:28:01
* @Entity com.hh.stock.domain.StockMarketLogPrice
*/
@Mapper
public interface StockMarketLogPriceMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockMarketLogPrice record);

    int insertSelective(StockMarketLogPrice record);

    StockMarketLogPrice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockMarketLogPrice record);

    int updateByPrimaryKey(StockMarketLogPrice record);

}
