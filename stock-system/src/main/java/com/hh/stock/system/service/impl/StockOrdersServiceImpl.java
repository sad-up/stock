package com.hh.stock.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hh.stock.common.core.domain.entity.StockOrders;
import com.hh.stock.system.service.StockOrdersService;
import com.hh.stock.system.mapper.StockOrdersMapper;
import org.springframework.stereotype.Service;

/**
* @author Huanghe
* @description 针对表【stock_orders】的数据库操作Service实现
* @createDate 2023-04-29 21:48:54
*/
@Service
public class StockOrdersServiceImpl extends ServiceImpl<StockOrdersMapper, StockOrders>
    implements StockOrdersService{

}




