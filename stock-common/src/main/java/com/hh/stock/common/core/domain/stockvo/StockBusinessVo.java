package com.hh.stock.common.core.domain.stockvo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author : hh
 * @date : 2023/4/2 16:34
 * @description : 定义封装返回前端个股主营业务信息的实体类
 */

@Data
public class StockBusinessVo {

    /**
     *  股票编码
     */
    @TableId
    private String code;

    /**
     * 股票名称
     */
    private String name;


    /**
     * 行业板块名称
     */
    private String trade;

    /**
     * 主营业务
     */
    private String business;

}
