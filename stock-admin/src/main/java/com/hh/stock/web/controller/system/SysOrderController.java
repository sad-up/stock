package com.hh.stock.web.controller.system;

import com.hh.stock.common.core.domain.AjaxResult;
import com.hh.stock.system.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : hh
 * @date : 2023/4/28 0:23
 * @description : 订单接口类
 */
@RestController
@RequestMapping("/api/order")
public class SysOrderController {

    @Autowired
    private OrdersService ordersService;

//    @PostMapping("/list")
//    public AjaxResult getOrdersList(HttpServletRequest request){
//        return ordersService.getOrdersList(request);
//    }

    /**
     * 新增订单
     * @param request
     * @return
     */
    @PostMapping
    public AjaxResult add(HttpServletRequest request){
        return ordersService.add(request);
    }

}
