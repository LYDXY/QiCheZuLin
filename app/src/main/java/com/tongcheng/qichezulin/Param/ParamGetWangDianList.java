package com.tongcheng.qichezulin.Param;

import com.tongcheng.qichezulin.config.AppConfig;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by 林尧 on 2016/7/26.
 */
@HttpRequest(
        host = AppConfig.HOST,
        path = AppConfig.GET_WANG_DIAN_LIST
)
public class ParamGetWangDianList extends RequestParams {
    public String county_id;//区县ID
    public String type_id;//网点类型
    public String keyword;//搜索字段
}
