package com.hh.stock.common.config.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : hh
 * @date : 2023/4/2 22:51
 * @description : some description
 */
@Data
@ConfigurationProperties(prefix = "task.pool")
public class TaskThreadPoolInfo {

    /**
     * 核心线程数
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
     * 线程得任务队列
     */
    private Integer queueCapacity;
}
