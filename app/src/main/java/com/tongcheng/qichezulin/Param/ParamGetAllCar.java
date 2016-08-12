package com.tongcheng.qichezulin.Param;

import com.tongcheng.qichezulin.config.AppConfig;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by 林尧 on 2016/7/26.
 */
@HttpRequest(
        host = AppConfig.HOST,
        path = AppConfig.GET_ALL_CAR_LIST
)
public class ParamGetAllCar extends RequestParams {
    public String page;//第几页
    public String page_size;//页数（不传默认10）
    public String cartype;//汽车类型
    public String paytype;//租凭类型【1.时2.日3月】
    public String min_price;//最小单价
    public String max_price;//最大单价
}