package com.hh.stock.common.core.domain.stockvo;

import java.math.BigDecimal;
import lombok.Data;


/**
 * @author : hh
 * @date : 2023/2/25 3:11
 * @description : 定义封装返回前端国内大盘数据的实体类
 */
@Data
public class InnerMarketDomain {
    /**
     * jdbc:bigint ->java:Long
     */
    private Long tradeAmt;
    /**
     * jdbc:decimal -> java:BigDecimal
     */
    private BigDecimal preClosePrice;
    private String code;
    private String name;
    private String curDate;
    private BigDecimal openPrice;
    private Long tradeVol;
    private BigDecimal upDown;
    private BigDecimal tradePrice;


}
