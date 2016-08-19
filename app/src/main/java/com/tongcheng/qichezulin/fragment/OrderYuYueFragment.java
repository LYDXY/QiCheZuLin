package com.tongcheng.qichezulin.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiongbull.jlog.JLog;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.tongcheng.qichezulin.Param.ParamOrderList;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.model.JsonBase2;
import com.tongcheng.qichezulin.model.OrderModel;
import com.tongcheng.qichezulin.pulltorefresh.PullToRefreshLayout;
import com.tongcheng.qichezulin.pulltorefresh.PullableListView;
import com.tongcheng.qichezulin.utils.UtilsJson;
import com.tongcheng.qichezulin.utils.UtilsUser;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by 林尧 on 2016/8/18.
 */

@ContentView(R.layout.fragment_order_yu_yue)

public class OrderYuYueFragment extends Fragment {


    @ViewInject(R.id.prl_prl_01) //上下拉控件
     PullToRefreshLayout prl_prl_01;
    @ViewInject(R.id.plv_order_yu_yue_list)
    PullableListView plv_order_yu_yue_list; //list 控件
    public Adapter adapter;

    String user_id="";
    String status="1";
    int page=1;
    String page_size="10";
    private boolean injected = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
        setOnPullListenerOnprl_prl();
        user_id= UtilsUser.getUser(getContext()).PID;
        get_order_yu_yue_list(user_id,status,page,page_size);
    }



    public void setOnPullListenerOnprl_prl() {
        prl_prl_01.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
               // get_order_yu_yue_list(user_id,status,page,page_size);
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        });
    }

    //获取预约订单数据
    public void get_order_yu_yue_list(String user_id,String status,int page,String page_size) {
        ParamOrderList paramOrderList = new ParamOrderList();
        paramOrderList.user_id=user_id;
        paramOrderList.status=status;
        paramOrderList.page=page+"";
        paramOrderList.page_size=page_size;
        Callback.Cancelable cancelable
                = x.http().post(paramOrderList, new Callback.CommonCallback<String>() {
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }


            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String result) {
                try {
                    UtilsJson.printJsonData(result);
                    Gson gson = new Gson();
                    Type type = new TypeToken<JsonBase2<List<OrderModel>>>() {
                    }.getType();
                    JsonBase2<List<OrderModel>> base = gson
                            .fromJson(result, type);
                    if (!base.status.toString().trim().equals("0")) {
                        if (base.data != null) {
                            JLog.w("获取预约订单成功");

                            if (adapter == null) {
                               adapter = new Adapter<OrderModel>(getActivity(), R.layout.listview_item_order_yu_ce) {
                                    @Override
                                    protected void convert(final AdapterHelper helper, OrderModel orderModel) {
                                        final int position = helper.getPosition();
                                        helper.setImageUrl(R.id.iv_car_picture,orderModel.FImg)
                                                .setText(R.id.tv_show_qu_che_shop, orderModel.FShopName)
                                                .setText(R.id.tv_show_qu_che_time, orderModel.FStartTime)
                                                .setText(R.id.tv_show_huan_che_time,orderModel.FEndTime)
                                                .setText(R.id.tv_show_price_all,orderModel.FCreateDate);


                                    }
                                };

                                adapter.addAll(base.data);
                                plv_order_yu_yue_list.setAdapter(adapter);
                            }
                            else {
                                adapter.addAll(base.data);
                                adapter.notifyDataSetChanged();
                            }


                        }
                    } else {
                        JLog.w("获取预约订单失败");
                    }
                } catch (Exception E) {
                    E.printStackTrace();
                }

            }
        });
    }


}
