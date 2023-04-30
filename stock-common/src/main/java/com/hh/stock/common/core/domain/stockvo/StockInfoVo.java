package com.hh.stock.common.core.domain.stockvo;

import lombok.Data;

/**
 * @author : hh
 * @date : 2023/4/29 13:29
 * @description : 个股的信息
 */
@Data
public class StockInfoVo {

    /**
     * 股票代码
     */
    private String code;

    /**
     * 股票名称
     */
    private String name;

    /**
     * 当前价格
     */
    private String tradePrice;

    /**
     * 时间
     */
    private String date;
}
