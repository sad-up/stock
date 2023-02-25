package com.hh.stock.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hh.stock.system.domain.StockBusiness;
import com.hh.stock.system.service.StockBusinessService;
import com.hh.stock.system.mapper.StockBusinessMapper;
import org.springframework.stereotype.Service;

/**
* @author Huanghe
* @description 针对表【stock_business(主营业务表)】的数据库操作Service实现
* @createDate 2023-02-11 18:35:21
*/
@Service
public class StockBusinessServiceImpl extends ServiceImpl<StockBusinessMapper, StockBusiness>
    implements StockBusinessService{

}




