package com.tongcheng.qichezulin.Param;

import com.tongcheng.qichezulin.config.AppConfig;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by 林尧 on 2016/7/26.
 */
@HttpRequest(
        host = AppConfig.HOST,
        path = AppConfig.SET_PW
)
public class ParamSetPassword extends RequestParams {
    public String user_id;//用户ID
    public String new_password;//新密码
    public String token;//密匙
}
