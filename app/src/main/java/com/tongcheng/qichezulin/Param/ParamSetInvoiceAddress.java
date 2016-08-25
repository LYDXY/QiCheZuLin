package com.tongcheng.qichezulin.Param;

import com.tongcheng.qichezulin.config.AppConfig;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by 林尧 on 2016/7/26.
 */
@HttpRequest(
        host = AppConfig.HOST,
        path = AppConfig.SET_INVOICE
)
public class ParamSetInvoiceAddress extends RequestParams {
    public String user_id;
    public String name;//联系人名称
    public String mobile;//手机号码
    public String prov;//省
    public String city;//城市
    public String dist;//区县
    public String invoiceaddress;//详细地址
    public String postcode;//邮政编码
}
