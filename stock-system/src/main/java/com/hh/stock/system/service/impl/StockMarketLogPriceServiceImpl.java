package com.hh.stock.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hh.stock.system.domain.StockMarketLogPrice;
import com.hh.stock.system.service.StockMarketLogPriceService;
import com.hh.stock.system.mapper.StockMarketLogPriceMapper;
import org.springframework.stereotype.Service;

/**
* @author Huanghe
* @description 针对表【stock_market_log_price(股票大盘 开盘价与前收盘价流水表)】的数据库操作Service实现
* @createDate 2023-02-11 18:35:22
*/
@Service
public class StockMarketLogPriceServiceImpl extends ServiceImpl<StockMarketLogPriceMapper, StockMarketLogPrice>
    implements StockMarketLogPriceService{

}




