package com.hh.stock.system.mapper;

import com.hh.stock.common.core.domain.stock.InnerMarketDomain;
import com.hh.stock.system.domain.StockMarketIndexInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
* @author Huanghe
* @description 针对表【stock_market_index_info(股票大盘数据详情表)】的数据库操作Mapper
* @createDate 2023-02-11 18:35:21
* @Entity com.hh.stock.domain.StockMarketIndexInfo
*/
@Mapper
public interface StockMarketIndexInfoMapper extends BaseMapper<StockMarketIndexInfo> {

    /**
     * 根据大盘ID和时间查询大盘信息
     * @param marketIds 大盘ID集合
     * @param timePoint 当前时间点（默认精确到分钟）
     * @return
     */
    List<InnerMarketDomain> getMarkInfo(@Param("marketIds") List<String> marketIds,@Param("timePoint") Date timePoint);
}




