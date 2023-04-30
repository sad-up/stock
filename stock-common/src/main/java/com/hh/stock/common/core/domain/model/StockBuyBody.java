package com.hh.stock.common.core.domain.model;

import lombok.Data;

/**
 * @author : hh
 * @date : 2023/4/29 17:53
 * @description : 购买股票类
 */
@Data
public class StockBuyBody {

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
     * 手数
     */
    private String num;

    /**
     * 总价
     */
    private String stocktotal;
}
