package com.hh.stock.system.mapper;

import com.hh.stock.common.core.domain.stockvo.*;
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
                                                      @Param("endTime") Date endTime);

    /**
     * 查询指定日期范围内指定股票每天的交易数据
     * @param stockCode 股票code
     * @param startTime 起始时间
     * @param endTime 终止时间
     * @return
     */
    List<StockEvrDayDomain> getStockInfoEvrDay(@Param("stockCode") String stockCode,
                                               @Param("startTime") Date startTime,
                                               @Param("endTime") Date endTime);


    /**
     * 批量插入功能
     * @param stockRtInfoList
     */
    int insertBatch(@Param("stockRtInfoList") List<StockRtInfo> stockRtInfoList);

    /**
     * 获取个股最新分时行情数据，主要包含：
     * 	开盘价、前收盘价、最新价、最高价、最低价、成交金额和成交量、交易时间信息;
     * @param code 股票编码
     * @return
     */
    List<StockDetail> getStockStockDetail(@Param("code") String code,
                                          @Param("startTime") Date startTime,
                                          @Param("endTime") Date endTime);


    /**
     * 功能描述：个股交易流水行情数据查询--查询最新交易流水，按照交易时间降序取前10
     * @param code
     * @return
     */
    List<StockScreenSecond> getStockScreenSecond(@Param("code") String code,
                                                 @Param("startTime") Date startTime,
                                                 @Param("endTime") Date endTime);

    /**
     * 功能描述：统计每周内的股票数据信息，信息包含：
     * 	股票ID、 一周内最高价、 一周内最低价 、周1开盘价、周5的收盘价、
     * 	整周均价、以及一周内最大交易日期（一般是周五所对应日期）;
     * @param code
     * @return
     */
    List<StockScreenWeekkline> getStockWeekKline(@Param("code") String code,
                                                 @Param("startTime") Date startTime,
                                                 @Param("endTime") Date endTime);

    /**
     * 功能描述：根据输入的个股代码，进行模糊查询，返回证券代码和证券名称
     * @param code
     * @return
     */
    List<StockSearchVo> getStockSearch(String code);

    /**
     * 获取个股股票信息
     * @return
     */
    List<StockInfoVo> getStockInfo(String code);
}




