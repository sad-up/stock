package com.hh.stock.job;

import com.hh.stock.system.service.StockTimerTaskService;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : hh
 * @date : 2023/3/27 19:43
 * @description : 定义配置xxljob执行任务的bean对象
 */
@Component
public class StockTimerJob {

    @Autowired
    private StockTimerTaskService stockTimerTaskService;

    /**
     * 测试执行器
     */
    @XxlJob("testXxlJob")
    public void testXxlJob() {
        System.out.println("testXxlJob run ..");
    }

    /**
     * 采集大盘数据
     */
    @XxlJob("getInnerMarketInfo")
    public void getInnerMarketInfo(){
        stockTimerTaskService.getInnerMarketInfo();
    }

    /**
     * 获取国内A股数据信息
     */
    @XxlJob("getAshareInfos")
    public void getAshareInfos(){
        stockTimerTaskService.getAshareInfo();
    }

    /**
     * 采集国外大盘数据
     */
    @XxlJob("getOuterMarketInfo")
    public void getOuterMarketInfo(){
        stockTimerTaskService.getOuterMarketInfo();
    }


    /**
     * 获取板块实时数据
     */
    @XxlJob("getStockSectorRtIndex")
    public  void getStockSectorRtIndex(){
        stockTimerTaskService.getStockSectorRtIndex();
    }

}
