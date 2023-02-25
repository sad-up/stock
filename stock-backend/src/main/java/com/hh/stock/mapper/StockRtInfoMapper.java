package com.hh.stock.mapper;

import com.hh.stock.domain.StockRtInfo;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Huanghe
* @description 针对表【stock_rt_info(个股详情信息表)】的数据库操作Mapper
* @createDate 2023-01-31 09:28:01
* @Entity com.hh.stock.domain.StockRtInfo
*/
@Mapper
public interface StockRtInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockRtInfo record);

    int insertSelective(StockRtInfo record);

    StockRtInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockRtInfo record);

    int updateByPrimaryKey(StockRtInfo record);

}
