package com.hh.stock.system.service.impl;

import com.google.common.collect.Lists;
import com.hh.stock.common.utils.DateTimeUtil;
import com.hh.stock.system.domain.StockBlockRtInfo;
import com.hh.stock.system.domain.StockExternalInfo;
import com.hh.stock.system.mapper.*;
import com.hh.stock.system.utils.stock.ParserStockInfoUtil;
import com.hh.stock.common.utils.uuid.IdWorker;
import com.hh.stock.common.config.vo.StockConfig;
import com.hh.stock.system.domain.StockMarketIndexInfo;
import com.hh.stock.system.domain.StockRtInfo;
import com.hh.stock.system.service.StockTimerTaskService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author : hh
 * @date : 2023/3/26 21:42
 * @description : 定义采集股票接口实现
 */
@Service("stockTimerTaskService")
@Slf4j
public class StockTimerTaskServiceImpl implements StockTimerTaskService {

    @Autowired
    private StockConfig stockConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Autowired
    private StockBusinessMapper stockBusinessMapper;

    @Autowired
    private ParserStockInfoUtil parserStockInfoUtil;

    @Autowired
    private StockRtInfoMapper stockRtInfoMapper;

    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;

    @Autowired
    private StockExternalInfoMapper stockExternalInfoMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 采集国内大盘数据实现
     */
    @Override
    public void getInnerMarketInfo() {
        // 1.定义采集的Url接口
        String url = stockConfig.getMarketUrl() + String.join(",",stockConfig.getInner());
        // 2.调用restTemplate采集数据
        // 2.1.组装
        HttpHeaders headers = new HttpHeaders();
        // 必须填写，否则数据采集不到
        headers.add("Referer","https://finance.sina.com.cn/stock/");
        headers.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36");
        // 2.2 组装请求对象
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        // 2.3 restTempl发起请求
        String resString = restTemplate.postForObject(url, entity, String.class);
        //log.info("当前采集的数据：{}",resString);
        // 4.解析数据，封装domain
        List<StockMarketIndexInfo> list = parserStockInfoUtil.parser4StockOrMarketInfo(resString, 1);
        log.info("采集的当前大盘数据：{}",list);
        // 批量保存
        stockMarketIndexInfoMapper.insertBatch(list);

    }

    /**
     * 采集国内A股股票详情信息
     */
    @Override
    public void getAshareInfo() {
        // 1.获取所有股票code的集合（3700+）
        List<String> stockCodeList = stockBusinessMapper.getAllStockCode();
        // 转化集合中的股票编码，添加前缀
        stockCodeList = stockCodeList.stream().map(id ->{
            return id.startsWith("6") ? "sh" + id : "sz" + id;
        }).collect(Collectors.toList());
        // 构造请求头
        HttpHeaders headers = new HttpHeaders();
        // 必须填写，否则数据采集不到
        headers.add("Referer","https://finance.sina.com.cn/stock/");
        headers.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36");
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        // 2.将股票集合分片处理，均等分，比如每份20
//        Lists.partition(stockCodeList,20).forEach(list->{
//            // 3.为每一份动态拼接url地址，然后通过restTempalte拉取数据
//            //拼接股票url地址
//            String Url=stockConfig.getMarketUrl()+String.join(",",list);
//            //获取响应数据
//            String resultData = restTemplate.postForObject(Url,entity,String.class);
//            // 4.解析数据，封装domain
//            List<StockRtInfo> stockRtInfos = parserStockInfoUtil.parser4StockOrMarketInfo(resultData, 3);
//            log.info("数据量：{}",stockRtInfos);
//            //5.批量插入
//            stockRtInfoMapper.insertBatch(stockRtInfos);
//        });
        //使用线程池
        Lists.partition(stockCodeList,20).forEach(list->{
            //加入线程池后异步多线程数据采集，这样提高了操作效率和数据库的插入效率（数据库io增加）
            threadPoolTaskExecutor.execute(() ->{
                // 3.为每一份动态拼接url地址，然后通过restTempalte拉取数据
                //拼接股票url地址
                String Url=stockConfig.getMarketUrl()+String.join(",",list);
                //获取响应数据
                String resultData = restTemplate.postForObject(Url,entity,String.class);
                // 4.解析数据，封装domain
                List<StockRtInfo> stockRtInfos = parserStockInfoUtil.parser4StockOrMarketInfo(resultData, 3);
                log.info("数据量：{}",stockRtInfos);
                //5.批量插入
                stockRtInfoMapper.insertBatch(stockRtInfos);
            });
        });
    }

    /**
     * 采集国外大盘信息
     */
    @Override
    public void getOuterMarketInfo(){
        // 1.定义采集的Url接口
        String url = stockConfig.getMarketUrl() + String.join(",",stockConfig.getOuter());
        // 2.调用restTemplate采集数据
        // 2.1.组装
        HttpHeaders headers = new HttpHeaders();
        // 必须填写，否则数据采集不到
        headers.add("Referer","https://finance.sina.com.cn/stock/");
        headers.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36");
        // 2.2 组装请求对象
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        // 2.3 restTempl发起请求
        String resString = restTemplate.postForObject(url, entity, String.class);
        // 4.解析数据，封装domain
        List<StockExternalInfo> list = parserStockInfoUtil.parser4StockOrMarketInfo(resString, 2);
        log.info("采集的当前国外大盘数据：{}",list);
        //5.批量插入
        stockExternalInfoMapper.insertBatch(list);
    }


    /**
     * 获取板块实时数据
     * http://vip.stock.finance.sina.com.cn/q/view/newSinaHy.php
     */
    @Override
    public void getStockSectorRtIndex() {
        //发送板块数据请求
        String result = restTemplate.getForObject(stockConfig.getBlockUrl(), String.class);
        //响应结果转板块集合数据
        List<StockBlockRtInfo> infos = parserStockInfoUtil.parse4StockBlock(result);
        log.info("板块数据量：{}",infos.size());
        //数据分片保存到数据库下 行业板块类目大概50个，可每小时查询一次即可
        Lists.partition(infos,20).forEach(list->{
            threadPoolTaskExecutor.execute(() ->{
                log.info("板块数据量：{}",infos.size());
                //20个一组，批量插入
                stockBlockRtInfoMapper.insertBatch(list);
            });
        });
    }



}
