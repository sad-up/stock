package com.hh.stock.service;

import com.hh.stock.domain.StockBusiness;

import java.util.List;

/**
 * @author : hh
 * @date : 2023/1/27 9:49
 * @description : 股票相关复习接口
 */

public interface StockService {

    /**
     *  查询所有得直营业务项
     * @return
     */
    List<StockBusiness> findAll();
}
