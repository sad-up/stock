package com.hh.stock.system.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.hh.stock.common.config.vo.StockConfig;
import com.hh.stock.common.core.domain.AjaxResult;
import com.hh.stock.common.core.domain.stockvo.*;
import com.hh.stock.common.utils.DateTimeUtil;
import com.hh.stock.system.domain.StockBusiness;
import com.hh.stock.system.mapper.*;
import com.hh.stock.system.service.StockService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author : hh
 * @date : 2023/2/25 2:51
 * @description : some description
 */


@Service("stockService")
public class StockServiceImpl implements StockService {

    @Autowired
    private StockBusinessMapper stockBusinessMapper;

    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Autowired
    private StockExternalInfoMapper stockExternalInfoMapper;
    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;

    @Autowired
    private StockRtInfoMapper stockRtInfoMapper;

    @Autowired
    private StockConfig stockConfig;



    @Override
    public List<StockBusiness> findAll() {
        return stockBusinessMapper.findAll();
    }


    /**
     * 获取最新得A股大盘信息
     * 如果不在股票交易日则显示最近得交易数据信息
     * @return
     */
    @Override
    public AjaxResult getNewAMarketInfo() {
        // 1. 获取国内A股大盘得id集合
        List<String> inners = stockConfig.getInner();
        // 2。 获取最近股票交易日期下对应的大盘信息
        DateTime lastDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        // 3. 将java中的Date
        Date lastDate = lastDateTime.toDate();
        //TODO mock测试数据，后期数据通过第三方接口动态获取试试数据，可删除
        //lastDate = DateTime.parse("2023-03-29 11:15:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // 4. 将获取的java Date传入接口
        List<InnerMarketDomain> list = stockMarketIndexInfoMapper.getMarkInfo(inners,lastDate);
        if(CollectionUtils.isEmpty(list)){
            return AjaxResult.error(AjaxResult.ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        return AjaxResult.success(list);
    }

    /**
     * 获取国外大盘最新数据
     * @return
     */
    @Override
    public AjaxResult getExternalAMarketInfo() {
        // 1. 获取外大盘得id集合
        List<String> outers = stockConfig.getOuter();
        // 2。 获取最近股票交易日期下对应的大盘信息
        DateTime lastDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date lastDate = lastDateTime.toDate();
        // 4. 将获取的java Date传入接口
        List<OuterMarketDomain> list = stockExternalInfoMapper.getMarkInfo(outers,lastDate);
        if(CollectionUtils.isEmpty(list)){
            return AjaxResult.error(AjaxResult.ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        return AjaxResult.success(list);
    }



    /**
     * 沪深两市板块分时行情数据查询
     * 以交易时间和交易总金额降序查询，取前10条数据
     * @return
     */
    @Override
    public AjaxResult sectorAllLimit() {
        DateTime lastDateTime = DateTimeUtil.getLastDate4Stock(new DateTime().minusMinutes(+1));
        Date lastDate = lastDateTime.toDate();
        // TODO  mock 数据
        //lastDate = DateTime.parse("2023-03-29 11:15:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //1.调用mapper接口获取数据
        List<StockBlockRtInfoVo> infos = stockBlockRtInfoMapper.sectorAllLimit(lastDate);
        // TODO 优化 避免全表查询 根据时间范围查询，提高查询效率
        //2.组装数据
        if(CollectionUtils.isEmpty(infos)){
            return AjaxResult.error(AjaxResult.ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        return AjaxResult.success(infos);
    }

    /**
     * 统计沪深两市个股票最新交易数据，并按涨幅降序排序查询前10条数据
     * @return
     */
    @Override
    public AjaxResult getStockRtInfoLimit() {
        // 1. 获取最近最新得股票有效交易时间点（精确到分钟）
        Date lastDate = DateTimeUtil.getLastDate4Stock(new DateTime().minusMinutes(+1)).toDate();
        // TODO  mock 数据
        //lastDate = DateTime.parse("2022-03-29 11:15:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // 2. 调用mapper查询
        List<StockUpdownDomain> list = stockBlockRtInfoMapper.getStockRtInfoLimit(lastDate);
        // 3.判断集合是否为空
        if (CollectionUtils.isEmpty(list)){
            return AjaxResult.error(AjaxResult.ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        return AjaxResult.success(list);
    }

    /**
     * 分页查询股票交易数据，按照日期和涨幅降序排序
     * @param page 当前页
     * @param pageSize 每页大小
     * @return
     */
    @Override
    public AjaxResult getStockRtInfoPages(Integer page, Integer pageSize) {
        // 1.设置分页参数
        PageHelper.startPage(page,pageSize);
        // 2.查询
        // 1. 获取最近最新得股票有效交易时间点（精确到分钟）
        Date lastDate = DateTimeUtil.getLastDate4Stock(new DateTime().minusMinutes(+1)).toDate();
        // TODO  mock 数据
        //lastDate = DateTime.parse("2022-01-14 15:00:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        List<StockUpdownDomain> pages = stockBlockRtInfoMapper.getStockRtInfoAll(lastDate);
        if (CollectionUtils.isEmpty(pages)) {
            return AjaxResult.error(AjaxResult.ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        // 3.组装PageInfo对象,封装了一切分页信息
        PageInfo<StockUpdownDomain> pageInfo = new PageInfo<>(pages);
        // 4. 转化成自定义得分页对象
        PageResult<StockUpdownDomain> pageResult = new PageResult<>(pageInfo);
        return AjaxResult.success(pageResult);
    }

    /**
     * 统计T日（最近一次股票交易日）涨停和跌停得分时统计
     * @return
     */
    @Override
    public AjaxResult getStockUpDownCount() {
        // 1.借助工具获取最近交易日得开盘时间和收盘时间
        // 获取有效时间点
        DateTime avableTimePoint = DateTimeUtil.getLastDate4Stock(DateTime.now());
        // 根据有效的时间点获取对应日期得开盘和收盘日期
        Date openTime = DateTimeUtil.getOpenDate(avableTimePoint).toDate();
        Date closeTime = DateTimeUtil.getCloseDate(avableTimePoint).toDate();
        // TODO  mock 数据
        //openTime = DateTime.parse("2021-12-19 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //closeTime = DateTime.parse("2021-12-19 15:00:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // 2.查询涨停得统计数据
        List<Map> upList = stockRtInfoMapper.getStockUpdownCount(openTime, closeTime,1);
        // 3.查询跌停得统计数据
        List<Map> downpList = stockRtInfoMapper.getStockUpdownCount(openTime, closeTime,0);
        // 4.组装map,将涨停和跌停得数据组装到map中
        HashMap<String, List> map = new HashMap<>();
        map.put("upList", upList);
        map.put("downList", downpList);
        // 5. 返回结果
        return AjaxResult.success(map);
    }

    /**
     * 到处股票信息到excel下
     * @param response 响应对象，可获取流对象
     * @param page 当前页
     * @param pageSize 每页大小
     */
    @Override
    public void stockExport(HttpServletResponse response, Integer page, Integer pageSize) throws IOException {
        try {
            // 1. 设置响应数据得类型：excel
            response.setContentType("application/vnd.ms-excel");
            // 2. 设置响应数据得编码格式
            response.setCharacterEncoding("utf-8");
            // 3. 设置默认文件名称
            // 这是URLEncoder.encode可以防止中文乱码，和easyexcel没关系
            String fileName = URLEncoder.encode("stockRt", "UTF-8") + ".xls";
            // 设置默认文件名称
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            // 读取导出的数据集合
            // 1.设置分页参数
            PageHelper.startPage(page, pageSize);
            // 2.查询
            // 1. 获取最近最新得股票有效交易时间点（精确到分钟）
            Date lastDate = DateTimeUtil.getLastDate4Stock(new DateTime().minusMinutes(+1)).toDate();
            // TODO mock 数据
//            lastDate = DateTime.parse("2022-01-14 15:00:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
            List<StockUpdownDomain> pages = stockBlockRtInfoMapper.getStockRtInfoAll(lastDate);
            if (CollectionUtils.isEmpty(pages)) {
                AjaxResult error = AjaxResult.error(AjaxResult.ResponseCode.NO_RESPONSE_DATA.getMessage());
                // 将错误信息转化成JSON字符串响应前端
                String jsonData = new Gson().toJson(error);
                response.getWriter().write(jsonData);
                //终止当前程序
                return;
            }
            // 将List<StockUpdownDomain>转化为List<StockExcelDomain>
            List<StockExcelDomain> domains = pages.stream().map(item -> {
                StockExcelDomain domain = new StockExcelDomain();
                BeanUtils.copyProperties(item, domain);
                return domain;
            }).collect(Collectors.toList());
            // 数据导出
            EasyExcel
                    .write(response.getOutputStream(), StockExcelDomain.class)
                    .inMemory(true) // 注意，此项配置不能少

                    .sheet("stockInfo")
                    .doWrite(domains);
            } catch (IOException e) {
                System.out.println("导出异常");
        }

    }

    /**
     * 统计国内A股大盘T日和T-1日成交量对比功能（成交量为沪市和深市成交量之和）
     * 如果当前日期不在股票交易日，则按照前一个有效股票交易日作为T日查询
     * @return
     */
    @Override
    public AjaxResult getStockTradeVolComparation() {
        // 1. 获取T日和T-1日得开始时间和结束时间
        // 1.1 获取最近股票交易日有效时间点 -- T日时间范围
        DateTime lastDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        DateTime openDateTime = DateTimeUtil.getOpenDate(lastDateTime);
        // 转换JAVA中得Date
        Date strartTimeForT = openDateTime.toDate();
        Date emdTimeForT = lastDateTime.toDate();
        // TODO  mock数据
        //strartTimeForT =  DateTime.parse("2022-01-03 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //emdTimeForT = DateTime.parse("2022-01-03 14:40:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // 1.2 获取T-1日区间范围
        DateTime preLastDateTime = DateTimeUtil.getPreviousTradingDay(lastDateTime);
        DateTime prepOenDateTime = DateTimeUtil.getOpenDate(preLastDateTime);
        // 转换JAVA中得Date
        Date startTimeForPreT = prepOenDateTime.toDate();
        Date emdTimeForPreT = preLastDateTime.toDate();
        // TODO mock数据
        //startTimeForPreT =  DateTime.parse("2022-01-02 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //emdTimeForPreT = DateTime.parse("2022-01-02 14:40:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // 2. 获取上证和深圳得大盘Id
        // 2.1 获取大盘Id集合
        List<String> marketIds = stockConfig.getInner();
        // 3. 分别查询T日和T-1日得数据，得到两个集合
        // 3.1 查询T日大盘统计交易数据
        List<Map> dataT = stockMarketIndexInfoMapper.getStockTradeVol(marketIds, strartTimeForT, emdTimeForT);
        if (CollectionUtils.isEmpty(dataT)) {
            dataT = new ArrayList<>();
        }
        // 3.2 查询T-1日大盘统计交易数据
        List<Map> dataPreT = stockMarketIndexInfoMapper.getStockTradeVol(marketIds, startTimeForPreT, emdTimeForPreT);
        if (CollectionUtils.isEmpty(dataPreT)) {
            dataPreT = new ArrayList<>();
        }
        // 4. 组装响应数据
        HashMap<String, List> info = new HashMap<>();
        info.put("volList", dataT);
        info.put("yesVolList", dataPreT);
        // 5. 返回数据
        return AjaxResult.success(info);
    }

    /**
     * 查询当前时间下股票的涨跌幅度区间统计功能
     * 如果当前日期不在有效时间内，则以最近的一个股票交易时间作为查询点
     * @return
     *   响应数据格式：
     *  {
     *     "code": 1,
     *     "data": {
     *         "time": "2021-12-31 14:58:00",
     *         "infos": [
     *             {
     *                 "count": 17,
     *                 "title": "-3~0%"
     *             },
     *             //...
     *             ]
     *     }
     */
    @Override
    public AjaxResult stockUpDownScopeCount() {
        //1.获取当前时间下最近的一个股票交易时间 精确到秒
        DateTime dateTimeStock = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date lastDate = dateTimeStock.toDate();
        //TODO 后续删除 mock-data
//        lastDate = DateTime.parse("2021-12-30 09:42:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //2.查询
        List<Map> infos = stockRtInfoMapper.getstockUpDownRegion(lastDate);
        if (CollectionUtils.isEmpty(infos)) {
            infos = new ArrayList<Map>();
        }
        // 保证涨幅区间按照从大到小顺序排序，且对于没有数据的涨幅区间默认为0
        //思路 按照顺序遍历自定义的涨幅区间获取对应map 就可以保证map对象的顺序
        //2.1 获取涨幅区间的顺序集合
        List<String> upDownRangeList = stockConfig.getUpDownRange();
        // 通过stream流map映射和过滤完成转换
        List<Map> finalInfos = infos;
        List<Map> newMaps = upDownRangeList.stream().map(item ->{
            Optional<Map> optional = finalInfos.stream().filter(map ->map.get("title").equals(item)).findFirst();
            Map tmp = null;
            // 判断是否有map
            if(optional.isPresent()){
                tmp = optional.get();
            }else {
                tmp = new HashMap<>();
                tmp.put("title", item);
                tmp.put("count",0);
            }
            return tmp;
        }).collect(Collectors.toList());
        //3.组装data
        HashMap<String, Object> data = new HashMap<>();
        String stringDateTime = dateTimeStock.toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        data.put("time", stringDateTime);
        data.put("infos", newMaps);
        //返回响应数据
        return AjaxResult.success(data);
    }

    /**
     * 功能描述：查询单个个股的分时行情数据，也就是统计指定股票T日每分钟的交易数据；
     *         如果当前日期不在有效时间内，则以最近的一个股票交易时间作为查询时间点
     * @param stockcode 股票编码
     * @return
     */
    @Override
    public AjaxResult stockScreenTimeSharing(String stockcode) {
        //1.获取最近有效的股票交易时间
        DateTime lastDateStock = DateTimeUtil.getLastDate4Stock(new DateTime().minusMinutes(+1));
        //获取当前日期
        Date endTime = lastDateStock.toDate();
        //TODO 后续删除 mock-data
        //String mockDate="2022-01-06 14:25:00";
        //endTime = DateTime.parse(mockDate, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //获取当前日期对应的开盘日期
        DateTime openDateTime = DateTimeUtil.getOpenDate(lastDateStock);
        Date startTime = openDateTime.toDate();
        //TODO 后续删除 mock-data
        //String openDateStr = "2022-01-06 09:30:00";
        //startTime = DateTime.parse(openDateStr, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        List<StockMinuteDomain> maps= stockRtInfoMapper.getstockScreenTimeSharing(stockcode,startTime,endTime);
        if(CollectionUtils.isEmpty(maps)) {
            maps = new ArrayList<>();
        }
        //响应前端
        return AjaxResult.success(maps);
    }


    /**
     * 功能描述：单个个股日K数据查询 ，可以根据时间区间查询数日的K线数据
     * 		默认查询历史20天的数据；
     * @param code 股票编码
     * @return
     */
    @Override
    public AjaxResult stockCreenDkLine(String code) {
        // 1.获取查询日期范围
        // 1.1 获取截止时间
        DateTime endDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date endTime = endDateTime.toDate();
        //TODO mock数据
        //endTime = DateTime.parse("2022-01-07 15:00:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // 1.2 获取开始时间 当前日期前推20天
        DateTime startDateTime = endDateTime.minusDays(20);
        Date startTime = startDateTime.toDate();
        //TODO mock数据
        //startTime = DateTime.parse("2022-01-07 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // 2. 调用mapper接口获取查询的集合数据
        List<StockEvrDayDomain> infos = stockRtInfoMapper.getStockInfoEvrDay(code, startTime , endTime);
        if(CollectionUtils.isEmpty(infos)) {
            infos = new ArrayList<>();
        }
        // 3.组装数据
        return AjaxResult.success(infos);
    }


    /**
     * 获取个股主营业务
     * @param code 股票id
     * @return
     */
    @Override
    public AjaxResult getStockDescribe(String code) {
        // 1.查询数据
        List<StockBusinessVo> infos = stockBusinessMapper.getBusinessInfo(code);
        // 2.判断是否为空
        if(CollectionUtils.isEmpty(infos)) {
            infos = new ArrayList<>();
        }
        // 3.组装数据
        return AjaxResult.success(infos);
    }

    /**
     * 获取个股最新分时行情数据，主要包含：
     * 	开盘价、前收盘价、最新价、最高价、最低价、成交金额和成交量、交易时间信息;
     * @param code 股票编码
     * @return
     */
    @Override
    public AjaxResult getStockStockDetail(String code) {
        //1.获取最近有效的股票交易时间
        DateTime lastDateStock = DateTimeUtil.getLastDate4Stock(new DateTime().minusMinutes(+1));
        //获取当前日期
        Date endTime = lastDateStock.toDate();
        //获取当前日期对应的开盘日期
        DateTime openDateTime = DateTimeUtil.getOpenDate(lastDateStock);
        Date startTime = openDateTime.toDate();
        // 1.查询数据
        List<StockDetail> infos = stockRtInfoMapper.getStockStockDetail(code,startTime,endTime);
        // 2.判断是否为空
        if(CollectionUtils.isEmpty(infos)) {
            infos = new ArrayList<>();
        }
        // 3.组装数据
        return AjaxResult.success(infos);
    }

    /**
     * 功能描述：根据输入的个股代码，进行模糊查询，返回证券代码和证券名称
     * @param code
     * @return
     */
    @Override
    public AjaxResult getStockSearch(String code) {
        List<StockSearchVo> infos = stockRtInfoMapper.getStockSearch(code);
        if(CollectionUtils.isEmpty(infos)) {
            infos = new ArrayList<>();
        }
        return AjaxResult.success(infos);
    }

    /**
     * 功能描述：个股交易流水行情数据查询--查询最新交易流水，按照交易时间降序取前10
     * @param code
     * @return
     */
    @Override
    public AjaxResult getStockScreenSecond(String code) {
        //1.获取最近有效的股票交易时间
        DateTime lastDateStock = DateTimeUtil.getLastDate4Stock(new DateTime().minusMinutes(+1));
        //获取当前日期
        Date endTime = lastDateStock.toDate();
        //获取当前日期对应的开盘日期
        DateTime openDateTime = DateTimeUtil.getOpenDate(lastDateStock);
        Date startTime = openDateTime.toDate();
        // 1.查询数据
        List<StockScreenSecond> infos = stockRtInfoMapper.getStockScreenSecond(code,startTime,endTime);
        // 2.判断是否为空
        if(CollectionUtils.isEmpty(infos)) {
            infos = new ArrayList<>();
        }
        // 3.组装数据
        return AjaxResult.success(infos);
    }

    /**
     * 功能描述：统计每周内的股票数据信息，信息包含：
     * 	股票ID、 一周内最高价、 一周内最低价 、周1开盘价、周5的收盘价、
     * 	整周均价、以及一周内最大交易日期（一般是周五所对应日期）;
     * @param code
     * @return
     */
    @Override
    public AjaxResult getStockWeekKline(String code) {
        // 1.获取查询日期范围
        // 1.1 获取截止时间
        DateTime endDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date endTime = endDateTime.toDate();
        //TODO mock数据
        //endTime = DateTime.parse("2022-01-07 15:00:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // 1.2 获取开始时间 当前日期前推20天
        DateTime startDateTime = DateTimeUtil.getDateTimeWithoutday(endDateTime.minusWeeks(20));
        Date startTime = DateTimeUtil.getFirstDayOfWeek(startDateTime.toDate());

        //TODO mock数据
        //startTime = DateTime.parse("2022-01-07 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // 2. 调用mapper接口获取查询的集合数据
        List<StockScreenWeekkline> infos = stockRtInfoMapper.getStockWeekKline(code, startTime , endTime);
        if(CollectionUtils.isEmpty(infos)) {
            infos = new ArrayList<>();
        }
        // 3.组装数据
        return AjaxResult.success(infos);
    }

}




