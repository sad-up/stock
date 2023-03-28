package com.hh.stock.system.mapper;

import com.hh.stock.common.core.domain.stockvo.StockEvrDayDomain;
import com.hh.stock.common.core.domain.stockvo.StockMinuteDomain;
import com.hh.stock.system.domain.StockRtInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author Huanghe
* @description 针对表【stock_rt_info(个股详情信息表)】的数据库操作Mapper
* @createDate 2023-02-11 18:35:22
* @Entity com.hh.stock.domain.StockRtInfo
*/
@Mapper
public interface StockRtInfoMapper extends BaseMapper<StockRtInfo> {

    /**
     * 根据指定日期范围统计对应范围内每分钟得涨停跌停
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param flag 标识： 1 涨停 0 跌停
     * @return
     */

    List<Map> getStockUpdownCount(@Param("startTime") Date startTime,
                                  @Param("endTime") Date endTime,
                                  @Param("flag") int flag);

    /**
     * 统计指定时间点下，各个涨跌区间内股票的个数
     * @param timePoint 股票交易时间点（精确到分钟）
     * @return
     */
    List<Map> getstockUpDownRegion(@Param("timePoint") Date timePoint);

    /**
     * 查询指定股票在指定日期下的每分钟的成交流水信息
     * @param stockCode 股票编码
     * @param startTime 最近的股票有效交易日期
     * @return
     */
    List<StockMinuteDomain> getstockScreenTimeSharing(@Param("stockCode") String stockCode,
                                                      @Param("startTime") Date startTime,
                                                      @Param("endtTime") Date endTime);

    /**
     * 查询指定日期范围内指定股票每天的交易数据
     * @param stockCode 股票code
     * @param startTime 起始时间
     * @param endTime 终止时间
     * @return
     */
    List<StockEvrDayDomain> getStockInfoEvrDay(@Param("stockCode") String stockCode,
                                               @Param("startTime") Date startTime,
                                               @Param("endtTime") Date endTime);


    /**
     * 批量插入功能
     * @param stockRtInfoList
     */
    int insertBatch(@Param("stockRtInfoList") List<StockRtInfo> stockRtInfoList);
}




