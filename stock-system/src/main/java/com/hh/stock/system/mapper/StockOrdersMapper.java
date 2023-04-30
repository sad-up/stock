package com.hh.stock.system.mapper;

import com.hh.stock.common.core.domain.entity.StockOrders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author Huanghe
* @description 针对表【stock_orders】的数据库操作Mapper
* @createDate 2023-04-29 21:48:54
* @Entity com.hh.stock.common.core.domain.entity.StockOrders
*/
public interface StockOrdersMapper extends BaseMapper<StockOrders> {

    /**
     * 生成股票订单信息
     * @param stockOrders
     */
    void insertInfo(StockOrders stockOrders);

    /**
     * 更新订单
     * @param stockOrders
     */
    void updateStatus(StockOrders stockOrders);
}




