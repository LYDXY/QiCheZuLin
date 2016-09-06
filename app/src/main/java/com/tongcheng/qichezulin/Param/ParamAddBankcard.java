package com.tongcheng.qichezulin.Param;

import com.tongcheng.qichezulin.config.AppConfig;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by 林尧 on 2016/7/26.
 */
@HttpRequest(
        host = AppConfig.HOST,
        path = AppConfig.ADD_BANKCARDN
)
public class ParamAddBankcard extends RequestParams {
    public String bankname;//银行卡名
    public String user_id;//用户id
    public String name;//用户姓名
    public String mobile;//银行预留手机号
    public String cardnum;//银行卡号
}
