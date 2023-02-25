package com.hh.stock.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 主营业务表
 * @TableName stock_business
 */
@TableName(value ="stock_business")
@Data
public class StockBusiness implements Serializable {
    /**
     *  股票编码
     */
    @TableId
    private String secCode;

    /**
     * 股票名称
     */
    private String secName;

    /**
     * 行业板块代码
     */
    private String sectorCode;

    /**
     * 行业板块名称
     */
    private String sectorName;

    /**
     * 主营业务
     */
    private String business;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}