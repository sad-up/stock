package com.hh.stock.common.core.domain.model;

import lombok.Data;

/**
 * @author : hh
 * @date : 2023/4/26 15:52
 * @description : 沙箱支付类
 */

@Data
public class AliPay {
    private String traceNo;
    private double totalAmount;
    private String subject;
    private String alipayTraceNo;

    private String id;


}
