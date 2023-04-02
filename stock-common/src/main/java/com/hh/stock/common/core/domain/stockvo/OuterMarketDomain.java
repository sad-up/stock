package com.hh.stock.common.core.domain.stockvo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author : hh
 * @date : 2023/3/30 23:33
 * @description : some description
 */
@Data
public class OuterMarketDomain {

    /**
     * jdbc:bigint ->java:Long
     */

    /**
     * jdbc:decimal -> java:BigDecimal
     */
    private BigDecimal curPoint;
    private String code;
    private String name;
    private String curDate;
    private BigDecimal upDownRate;

    private BigDecimal upDownPoint;

}
