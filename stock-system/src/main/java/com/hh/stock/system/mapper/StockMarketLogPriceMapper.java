package com.hh.stock.system.mapper;

import com.hh.stock.system.domain.StockMarketLogPrice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Huanghe
* @description 针对表【stock_market_log_price(股票大盘 开盘价与前收盘价流水表)】的数据库操作Mapper
* @createDate 2023-02-11 18:35:22
* @Entity com.hh.stock.domain.StockMarketLogPrice
*/
@Mapper
public interface StockMarketLogPriceMapper extends BaseMapper<StockMarketLogPrice> {

}




