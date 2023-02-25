package com.hh.stock.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 股票大盘 开盘价与前收盘价流水表
 * @TableName stock_market_log_price
 */
@TableName(value ="stock_market_log_price")
@Data
public class StockMarketLogPrice implements Serializable {
    /**
     * 主键ID
     */
    @TableId
    private String id;

    /**
     * 大盘编码
     */
    private String marketCode;

    /**
     * 当前日期
     */
    private Date curDate;

    /**
     * 前收盘价格
     */
    private BigDecimal preClosePrice;

    /**
     * 开盘价格
     */
    private BigDecimal openPrice;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}