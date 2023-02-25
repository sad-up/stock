package com.hh.stock.service.impl;

import com.hh.stock.domain.StockBusiness;
import com.hh.stock.mapper.StockBusinessMapper;
import com.hh.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : hh
 * @date : 2023/1/27 10:01
 * @description : some description
 */
@Service("stockService")
public class StockServiceImpl implements StockService {

    @Autowired
    private StockBusinessMapper stockBusinessMapper;

    @Override
    public List<StockBusiness> findAll() {
        return stockBusinessMapper.findAll();
    }
}
