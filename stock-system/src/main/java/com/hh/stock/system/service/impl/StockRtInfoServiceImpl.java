package com.hh.stock.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hh.stock.system.domain.StockRtInfo;
import com.hh.stock.system.service.StockRtInfoService;
import com.hh.stock.system.mapper.StockRtInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author Huanghe
* @description 针对表【stock_rt_info(个股详情信息表)】的数据库操作Service实现
* @createDate 2023-02-11 18:35:22
*/
@Service
public class StockRtInfoServiceImpl extends ServiceImpl<StockRtInfoMapper, StockRtInfo>
    implements StockRtInfoService{

}




