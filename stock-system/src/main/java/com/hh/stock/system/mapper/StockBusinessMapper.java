package com.hh.stock.system.mapper;

import com.hh.stock.system.domain.StockBusiness;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author Huanghe
* @description 针对表【stock_business(主营业务表)】的数据库操作Mapper
* @createDate 2023-02-11 18:35:21
* @Entity com.hh.stock.domain.StockBusiness
*/
@Mapper
public interface StockBusinessMapper extends BaseMapper<StockBusiness> {

    List<StockBusiness> findAll();

    /**
     * 获取所有股票的编码
     * @return
     */
    List<String> getAllStockCode();
}




