package com.hh.stock.system.mapper;

import com.hh.stock.common.core.domain.entity.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author Huanghe
* @description 针对表【orders】的数据库操作Mapper
* @createDate 2023-04-27 20:06:53
* @Entity com.hh.stock.common.core.domain.entity.Orders
*/
public interface OrdersMapper extends BaseMapper<Orders> {

    /**
     * 根据用户支付宝唯一Id查询订单id
     * @param buyId
     * @return
     */
    String selectOrderbuyId(String buyId);


    /**
     * 新增订单
     * @param orders
     * @return
     */
    void insertOders(Orders orders);

    /**
     * 通过订单号更新订单
     * @param orders
     */
    void updateByOrders(Orders orders);

    /**
     * 查找用户购买对应的id
     * @param username
     * @return
     */
    String selectOrderUsername(String username);


}




