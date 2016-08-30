package com.tongcheng.qichezulin.Param;

import com.tongcheng.qichezulin.config.AppConfig;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by 林尧 on 2016/7/26.
 */
@HttpRequest(
        host = AppConfig.HOST,
        path = AppConfig.UPDATE_USER
)
public class ParamUpdateUser extends RequestParams {
    public String user_id;//用户ID
    public String phone;//手机号码
    public String name;//昵称
    public String sex;//性别 1 男 0 女
}
