package com.tongcheng.qichezulin.Param;

import com.tongcheng.qichezulin.config.AppConfig;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by 林尧 on 2016/7/26.
 */
@HttpRequest(
        host = AppConfig.HOST,
        path = AppConfig.GET_DEFAULTSHOP
)
public class ParamNearWangDian extends RequestParams {
    public String lng;//经度
    public String lat;//纬度
    public String type_id;//网店类型
}
