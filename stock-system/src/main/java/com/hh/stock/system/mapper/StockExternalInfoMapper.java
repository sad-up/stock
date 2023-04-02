package com.hh.stock.system.mapper;

import com.hh.stock.common.core.domain.stockvo.OuterMarketDomain;
import com.hh.stock.system.domain.StockExternalInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hh.stock.system.domain.StockRtInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
* @author Huanghe
* @description 针对表【stock_external_info】的数据库操作Mapper
* @createDate 2023-03-30 23:39:55
* @Entity com.hh.stock.system.domain.StockExternalInfo
*/
public interface StockExternalInfoMapper extends BaseMapper<StockExternalInfo> {

    /**
     * 根据大盘ID和时间查询大盘信息
     * @param marketIds 大盘ID集合
     * @param timePoint 当前时间点（默认精确到分钟）
     * @return
     */
    List<OuterMarketDomain> getMarkInfo(@Param("marketIds") List<String> marketIds,@Param("timePoint") Date timePoint);


    /**
     * 批量保存大盘数据
     * @param list
     */
    void insertBatch(@Param("stockExternalInfoList") List<StockExternalInfo> list);
}




