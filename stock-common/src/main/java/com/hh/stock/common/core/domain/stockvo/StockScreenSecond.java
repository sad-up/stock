package com.hh.stock.common.core.domain.stockvo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author : hh
 * @date : 2023/4/3 23:04
 * @description : 义封装返回前端个股交易流水行情数据信息的实体类
 */
@Data
public class StockScreenSecond {

    /**
     * 日期，eg:2022-01-28 08:09
     */
    private String date;

    /**
     * 交易量
     */
    private Long tradeAmt;

    /**
     * 当前交易总金额
     */
    private BigDecimal tradeVol;
    /**
     * 当前价格
     */
    private BigDecimal tradePrice;

}
