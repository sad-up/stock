package com.hh.stock.common.config;


import com.hh.stock.common.utils.uuid.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : hh
 * @date : 2023/1/27 13:18
 * @description : 定义公共配置类
 */

@Configuration
public class CommonConfig {


    /**
     * 配置基于雪花算法生成全局唯一id
     * 参与运算的参数： 时间戳+ 机房id + 机器id + 序列号
     * 保证唯一id
     * @return
     */
    @Bean
    public IdWorker idWorker(){
        // 指定当前为1号机房的2号机器生成
        return new IdWorker(2L, 1L);
    }

}
