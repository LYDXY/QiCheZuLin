package com.tongcheng.qichezulin.Param;

import com.tongcheng.qichezulin.config.AppConfig;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by 林尧 on 2016/7/26.
 */
@HttpRequest(
        host = AppConfig.HOST,
        path = AppConfig.MY_SEVICE_WEI_XIN
)
public class ParamWeiXin extends RequestParams {
    public String out_trade_no;//订单号
    public String body;//商品描述
    public String detail;//商品详细
    public String total_fee;//总金额
    public String trade_type;//交易类型
}
