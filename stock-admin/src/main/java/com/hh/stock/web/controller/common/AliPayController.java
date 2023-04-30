package com.hh.stock.web.controller.common;



import cn.hutool.json.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.hh.stock.common.config.AliPayConfig;
import com.hh.stock.common.core.domain.entity.Orders;
import com.hh.stock.system.mapper.AssetMapper;
import com.hh.stock.system.mapper.OrdersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.hh.stock.common.core.domain.model.AliPay;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : hh
 * @date : 2023/4/26 15:44
 * @description : 支付接口
 */
@ResponseBody
@RestController
@RequestMapping("/api/alipay")
public class AliPayController {

    private static final String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";
    private static final String FORMAT = "JSON";
    private static final String CHARSET = "UTF-8";
    //签名方式
    private static final String SIGN_TYPE = "RSA2";

    private static final String RETURN_URL = "http://localhost:8089/user/profile";

    @Resource
    private AliPayConfig aliPayConfig;

    @Autowired
    private OrdersMapper ordersMapper;


    @Autowired
    private AssetMapper assetMapper;

    @GetMapping("/pay") // &subject=xxx&traceNo=xxx&totalAmount=xxx
    public void pay(AliPay aliPay, HttpServletResponse httpResponse) throws Exception {
        // 1. 创建Client，通用SDK提供的Client，负责调用支付宝的API
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, aliPayConfig.getAppId(),
                aliPayConfig.getAppPrivateKey(), FORMAT, CHARSET, aliPayConfig.getAlipayPublicKey(),SIGN_TYPE);


        // 2. 创建 Request并设置Request参数
        // 发送请求的 Request类
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        request.setReturnUrl(aliPayConfig.getReturnUrl());
        JSONObject bizContent = new JSONObject();
        // 我们自己生成的订单编号
        bizContent.set("out_trade_no", aliPay.getTraceNo());
        // 订单的总金额
        bizContent.set("total_amount", aliPay.getTotalAmount());
        // 支付的名称
        bizContent.set("subject", aliPay.getSubject());
        // 固定配置
        bizContent.set("product_code", "FAST_INSTANT_TRADE_PAY");
        bizContent.set("id", aliPay.getId());
        request.setBizContent(bizContent.toString());

        // 通过id修改订单状态
        Orders orders = new Orders();
        orders.setId(aliPay.getId());
        // 更新订单未支付
        orders.setStatus("2");
        ordersMapper.updateById(orders);

        // 执行请求，拿到响应的结果，返回给浏览器
        String form = "";
        try {
            // 调用SDK生成表单
            form = alipayClient.pageExecute(request).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        // 直接将完整的表单html输出到页面
        httpResponse.getWriter().write(form);
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();


    }
    // http://localhost:8098/api/alipay/pay?subject=112233999&traceNo=121319993123&totalAmount=1

    @PostMapping("/notify")  // 注意这里必须是POST接口
    public String payNotify(HttpServletRequest request) throws Exception {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("=========支付宝异步回调========");

            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
                 System.out.println(name + " = " + request.getParameter(name));
            }

            String outTradeNo = params.get("out_trade_no");
            String gmtPayment = params.get("gmt_payment");
            String alipayTradeNo = params.get("trade_no");
            BigDecimal buyerPayAmount = new BigDecimal(params.get("buyer_pay_amount"));
            String subject = params.get("subject");
            String buyerId = params.get("buyer_id");

            String sign = params.get("sign");
            String content = AlipaySignature.getSignCheckContentV1(params);
            boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, aliPayConfig.getAlipayPublicKey(), "UTF-8"); // 验证签名
            // 支付宝验签
            if (checkSignature) {
                // 验签通过
                System.out.println("交易名称: " + params.get("subject"));
                System.out.println("交易状态: " + params.get("trade_status"));
                System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
                System.out.println("商户订单号: " + params.get("out_trade_no"));
                System.out.println("交易金额: " + params.get("total_amount"));
                System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
                System.out.println("买家付款时间: " + params.get("gmt_payment"));
                System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));

                // 查询订单
                String orders_id = ordersMapper.selectOrderbuyId(buyerId);


                if (!"".equals(orders_id) ) {
                    Orders orders = new Orders();
                    orders.setId(orders_id);
                    orders.setName(subject);
                    orders.setAlipayNo(alipayTradeNo);
                    orders.setTradeNo(outTradeNo);
                    orders.setCash(buyerPayAmount);
                    // 更新订单支付完成
                    orders.setStatus("1");
                    // 更新订单
                    ordersMapper.updateByOrders(orders);
                    assetMapper.updateCash(buyerId, buyerPayAmount);
                }
            }
        }
        return "success";
    }





}
