package com.hh.stock.common.config.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : hh
 * @date : 2023/3/28 16:40
 * @description : some description
 */

@ConfigurationProperties(prefix = "task.pool")
@Data
public class TaskThreadPoolInfo {
    /**
     *  核心线程数（获取硬件）：线程池创建时候初始化的线程数
     */
    private Integer corePoolSize;
    /**
     * 最大线程数
     */
    private Integer maxPoolSize;

    /**
     * 空闲线程最大存活时间
     */
    private Integer keepAliveSeconds;

    /**
     * 线程的任务队列
     */
    private Integer queueCapacity;
}