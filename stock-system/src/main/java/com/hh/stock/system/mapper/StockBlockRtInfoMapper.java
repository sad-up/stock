package com.hh.stock.system.mapper;

import com.hh.stock.common.core.domain.stockvo.StockBlockRtInfoVo;
import com.hh.stock.common.core.domain.stockvo.StockUpdownDomain;
import com.hh.stock.system.domain.StockBlockRtInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.Date;
import java.util.List;

/**
* @author Huanghe
* @description 针对表【stock_block_rt_info(股票板块详情信息表)】的数据库操作Mapper
* @createDate 2023-02-11 18:35:21
* @Entity com.hh.stock.domain.StockBlockRtInfo
*/
@Mapper
public interface StockBlockRtInfoMapper extends BaseMapper<StockBlockRtInfo> {

    /**
     * 沪深两市板块分时行情数据查询，
     * 以交易时间和交易总金额降序查询，取前10条数据
     * @return
     */
    List<StockBlockRtInfoVo> sectorAllLimit(Date lastDate);

    /**
     * 查询指定时间点下数据，然后根据涨幅排序，取前10
     * @param timePoint 指定日期
     * @return
     */
    List<StockUpdownDomain> getStockRtInfoLimit(@Param("timePoint") Date timePoint);

    /**
     * 根据日期和涨幅降序排序查询股票信息
     * @param timePoint 指定日期
     * @return
     */
    List<StockUpdownDomain> getStockRtInfoAll(@Param("timePoint") Date timePoint);


    /**
     * 板块信息批量插入
     * @param list
     * @return
     */
    int insertBatch(List<StockBlockRtInfo> list);
}




