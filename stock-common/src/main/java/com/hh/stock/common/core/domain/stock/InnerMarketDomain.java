package com.hh.stock.common.core.domain.stock;

import java.math.BigDecimal;

/**
 * @author : hh
 * @date : 2023/2/25 3:11
 * @description : 定义封装国内大盘数据的实体类
 */

public class InnerMarketDomain {
    /**
     * jdbc:bigint ->java:Long
     */
    private Long tradeAmt;
    /**
     * jdbc:decimal -> java:BigDecimal
     */
    private BigDecimal perClosePrice;
    private String code;
    private String name;
    private String curDate;
    private BigDecimal openPrice;
    private Long tradeVol;
    private BigDecimal upDown;
    private BigDecimal tradePrice;


}
