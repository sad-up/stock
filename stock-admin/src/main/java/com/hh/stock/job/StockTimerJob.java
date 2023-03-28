package com.hh.stock.job;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

/**
 * @author : hh
 * @date : 2023/3/27 19:43
 * @description : 定义配置xxljob执行任务的bean对象
 */
@Component
public class StockTimerJob {

    @XxlJob("testXxlJob")
    public void testXxlJob() {
        System.out.println("testXxlJob run ..");
    }
}
