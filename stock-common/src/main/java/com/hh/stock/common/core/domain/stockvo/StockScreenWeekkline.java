package com.hh.stock.common.core.domain.stockvo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author : hh
 * @date : 2023/4/10 0:21
 * @description : 定义封装返回前端个股周K线数据信息的实体类
 */
@Data
public class StockScreenWeekkline {

    /**
     * 一周内平均价
     */
    private BigDecimal avgPrice;

    /**
     * 一周内最低价
     */
    private BigDecimal minPrice;

    /**
     * 一周内最高价
     */
    private BigDecimal maxPrice;

    /**
     * 周一开盘价
     */
    private BigDecimal openPrice;

    /**
     * 周五收盘价（如果当前日期不到周五，则显示最新价格）
     */
    private BigDecimal closePrice;

    /**
     * 一周内最大时间
     */
    private String maxTime;

    /**
     * 股票编码
     */
    private String code;

    /**
     * 周
     */
    private String week;
}
