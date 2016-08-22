package com.tongcheng.qichezulin.Param;

import com.tongcheng.qichezulin.config.AppConfig;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by 林尧 on 2016/7/26.
 */
@HttpRequest(
        host = AppConfig.HOST,
        path = AppConfig.GET_CARLIST
)
public class ParamChooseCar extends RequestParams {
    public String shop_id;//门店id
    public String is_auto;//是否帮我选车
    public String type;//车辆分类
    public String paytype;//租凭类型【1.时2.日3月】
    public String min_price;//最小单价
    public String max_price; //最大单价
}
