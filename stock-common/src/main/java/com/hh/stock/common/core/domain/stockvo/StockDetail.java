package com.hh.stock.common.core.domain.stockvo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author : hh
 * @date : 2023/4/3 8:51
 * @description : 定义封装返回前端个股最新分时行情数据信息的实体类
 */

@Data

public class StockDetail {

    /**
     * 日期，eg:2022-01-28 08:09
     */
    private String date;
    /**
     * 交易量
     */
    private Long tradeAmt;

    /**
     * 最低价
     */
    private BigDecimal lowPrice;
    /**
     * 前收盘价
     */
    private BigDecimal preClosePrice;

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
