package com.hh.stock.system.service;

import com.hh.stock.common.core.domain.AjaxResult;
import com.hh.stock.system.domain.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author Huanghe
* @description 针对表【orders】的数据库操作Service
* @createDate 2023-04-27 20:06:53
*/
public interface OrdersService extends IService<Orders> {

    /**
     * 新增订单
     * @param request
     * @return
     */
    AjaxResult add(HttpServletRequest request);
}
