package com.tongcheng.qichezulin.Param;

import com.tongcheng.qichezulin.config.AppConfig;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by 林尧 on 2016/7/26.
 */
@HttpRequest(
        host = AppConfig.HOST,
        path = AppConfig.GET_MESSAGELIST
)
public class ParamGetMessage extends RequestParams {
    public String user_id;//用户ID（个人通知才需要传入）
    public String type_id;//消息类型[1.短信2.系统通知3.个人通知4.其他消息]
}
