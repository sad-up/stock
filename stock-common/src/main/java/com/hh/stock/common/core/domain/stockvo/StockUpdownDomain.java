package com.hh.stock.common.core.domain.stockvo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author : hh
 * @date : 2023/3/18 21:14
 * @description : 股票涨跌信息
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockUpdownDomain {
    /**
     * 交易量
     */
    private Long tradeAmt;
    /**
     * 前收盘价
     */
    private BigDecimal preClosePrice;
    /**
     * 振幅
     */
    private BigDecimal amplitude;
    /**
     * 股票编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 日期
     */
    private String curDate;
    /**
     * 交易金额
     */
    private BigDecimal tradeVol;
    /**
     * 张涨跌
     */
    private BigDecimal increase;

    /**
     * 涨幅
     */
    private BigDecimal upDown;
    /**
     * 当前价格
     */
    private BigDecimal tradePrice;

}

