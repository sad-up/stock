package com.hh.stock.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hh.stock.common.core.domain.AjaxResult;
import com.hh.stock.common.utils.uuid.IdWorker;
import com.hh.stock.system.domain.Orders;
import com.hh.stock.system.mapper.AssetMapper;
import com.hh.stock.system.service.OrdersService;
import com.hh.stock.system.mapper.OrdersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
* @author Huanghe
* @description 针对表【orders】的数据库操作Service实现
* @createDate 2023-04-27 20:06:53
*/
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
    implements OrdersService{

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private AssetMapper assetMapper;

    /**
     * 新增订单
     * @param request
     * @return
     */
    @Override
    public AjaxResult add(HttpServletRequest request) {
        Orders orders = new Orders();
        orders.setId(idWorker.nextId()+"");
        orders.setStatus("0");
        orders.setUsername(request.getParameter("username"));
        //通过用户名查出buy_id
        orders.setBuyId(assetMapper.findBuyIdByUsername(request.getParameter("username")));
        ordersMapper.insertOders(orders);
        return AjaxResult.success("成功");

    }
}




