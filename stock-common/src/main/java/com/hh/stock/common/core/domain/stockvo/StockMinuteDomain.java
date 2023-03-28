package com.hh.stock.common.core.domain.stockvo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author : hh
 * @date : 2023/3/25 20:29
 * @description : 个股分时数据封装
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockMinuteDomain {

    /**
     * 日期，eg:202201280809
     */
    private String date;
    /**
     * 交易量
     */
    private Long tradeAmt;
    /**
     * 股票编码
     */
    private String code;
    /**
     * 最低价
     */
    private BigDecimal lowPrice;
    /**
     * 前收盘价
     */
    private BigDecimal preClosePrice;
    /**
     * 股票名称
     */
    private String name;
    /**
     * 最高价
     */
    private BigDecimal highPrice;
    /**
     * 开盘价
     */
    private BigDecimal openPrice;

    /**
     * 当前交易总金额
     */
    private BigDecimal tradeVol;
    /**
     * 当前价格
     */
    private BigDecimal tradePrice;

}
