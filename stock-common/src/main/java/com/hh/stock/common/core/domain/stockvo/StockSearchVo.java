package com.hh.stock.common.core.domain.stockvo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author : hh
 * @date : 2023/4/24 11:34
 * @description : some description
 */
@Data
public class StockSearchVo {

    /**
     *  股票编码
     */
    @TableId
    private String code;

    /**
     * 股票名称
     */
    private String name;
}
