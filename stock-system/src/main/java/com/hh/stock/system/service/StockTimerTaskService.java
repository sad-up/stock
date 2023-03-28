package com.hh.stock.system.service;

/**
 * @author : hh
 * @date : 2023/3/26 21:41
 * @description : 定义采集股票数据的定时任务的服务接口
 */

public interface StockTimerTaskService {

    /**
     * 获取国内大盘的实时数据信息
     */
    void getInnerMarketInfo();

    /**
     * 采集国内A股股票详情信息
     */
    void getAshareInfo();

    /**
     * 获取板块数据
     */
    void getStockSectorRtIndex();
}
