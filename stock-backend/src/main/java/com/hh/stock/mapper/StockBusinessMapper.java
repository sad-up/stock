package com.hh.stock.mapper;

import com.hh.stock.domain.StockBusiness;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author Huanghe
* @description 针对表【stock_business(主营业务表)】的数据库操作Mapper
* @createDate 2023-01-31 09:28:01
* @Entity com.hh.stock.domain.StockBusiness
*/
@Mapper
public interface StockBusinessMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockBusiness record);

    int insertSelective(StockBusiness record);

    StockBusiness selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockBusiness record);

    int updateByPrimaryKey(StockBusiness record);

    List<StockBusiness> findAll();

}
