package com.tongcheng.qichezulin.Param;

import com.tongcheng.qichezulin.config.AppConfig;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by 林尧 on 2016/7/26.
 */
@HttpRequest(
        host = AppConfig.HOST,
        path = AppConfig.SET_ORDER
)
public class ParamSetOrder extends RequestParams {
    public String user_id;//用户ID
    public String car_id;//汽车ID
    public String shop_id;//门店ID
    public String start_time;//租车开始时间
    public String end_time;//租车结束时间
    public String deposit;//定金金额
    public String money;//全部金额
    public String lessmoney;//剩余金额
    public String jifen;//使用积分
    public String expenseid;//收费id(格式1 ,2 ,3)
    public String invoiceId;//发票抬头ID
    public String invoiceaddressId;//发票地址ID
    public String paytype;//定金支付类型（1.钱包2.支付宝3.微信）
}
