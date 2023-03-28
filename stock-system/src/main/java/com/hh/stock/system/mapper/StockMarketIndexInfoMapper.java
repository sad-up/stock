package com.hh.stock.system.mapper;

import com.hh.stock.common.core.domain.stockvo.InnerMarketDomain;
import com.hh.stock.system.domain.StockMarketIndexInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    List<InnerMarketDomain> getMarkInfo(@Param("marketIds") List<String> marketIds, @Param("timePoint") Date timePoint);

    /**
     * 根据时间范围和指定大盘Id统计每分钟的交易量
     * @param marketIds 大盘Id集合
     * @param strartTime 交易开始时间
     * @param emdTime 交易结束时间
     * @return
     */
    @MapKey("markedIds")
    List<Map> getStockTradeVol(@Param("marketIds") List<String> marketIds,
                               @Param("strartTime") Date strartTime,
                               @Param("emdTime") Date emdTime);

    /**
     * 批量保存大盘数据
     * @param list
     */
    void insertBatch(@Param("stockMarketInfoList") List<StockMarketIndexInfo> list);
}




