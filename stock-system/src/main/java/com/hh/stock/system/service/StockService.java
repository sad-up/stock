package com.hh.stock.system.service;

import com.hh.stock.common.core.domain.AjaxResult;
import com.hh.stock.system.domain.StockBusiness;

import java.util.List;

/**
 * @author : hh
 * @date : 2023/2/25 2:44
 * @description : 股票相关复习接口
 */

public interface StockService {

    /**
     *  查询所有得直营业务项
     * @return
     */
    List<StockBusiness> findAll();

    /**
     * 获取最新得A股大盘信息
     * 如果不在股票交易日则显示最近得交易数据信息
     * @return
     */
    AjaxResult getNewAMarketInfo();
}
