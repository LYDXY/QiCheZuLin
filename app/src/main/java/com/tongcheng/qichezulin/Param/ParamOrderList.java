package com.tongcheng.qichezulin.Param;

import com.tongcheng.qichezulin.config.AppConfig;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by 林尧 on 2016/7/26.
 */
@HttpRequest(
        host = AppConfig.HOST,
        path = AppConfig.GET_GET_ORDERLIST
)
public class ParamOrderList extends RequestParams {
   public String user_id; //用户id
   public String status; //订单状态1.预约 2.租凭中 3.已完成 4.已取消
   public String page;//第几页
   public String page_size;//页数（不传默认10）
}
