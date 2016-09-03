package com.tongcheng.qichezulin.fragment;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiongbull.jlog.JLog;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.tongcheng.qichezulin.Param.ParamBackCar;
import com.tongcheng.qichezulin.Param.ParamOrderList;
import com.tongcheng.qichezulin.Param.ParamQuXiaoOrder;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.model.JsonBase2;
import com.tongcheng.qichezulin.model.OrderModel;
import com.tongcheng.qichezulin.model.SetOrderModel;
import com.tongcheng.qichezulin.pulltorefresh.PullToRefreshLayout;
import com.tongcheng.qichezulin.pulltorefresh.PullableListView;
import com.tongcheng.qichezulin.utils.Utils;
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

@ContentView(R.layout.fragment_order_zu_lin_zhong)
public class OrderZuLinZhongFragment extends Fragment {

    public Adapter adapter;
    String user_id = "";
    String status = "2";
    int page = 1;
    String page_size = "10";
    @ViewInject(R.id.prl_prl_02) //上下拉控件
            PullToRefreshLayout prl_prl_02;
    @ViewInject(R.id.plv_order_zu_lin_zhong_list)
    PullableListView plv_order_zu_lin_zhong_list; //list 控件
    private AlertView mAlertView;
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
        user_id = UtilsUser.getUser(getContext()).PID;
        get_order_yu_yue_list(user_id, status, page, page_size);
    }

    public void setOnPullListenerOnprl_prl() {
        prl_prl_02.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                page = 1;
                adapter.clear();
                get_order_yu_yue_list(user_id, status, page, page_size);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                page++;
                JLog.w("第几页:" + page);
                get_order_yu_yue_list(user_id, status, page, page_size);
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        });
    }

    //获取租赁中的订单数据
    public void get_order_yu_yue_list(String user_id, String status, int page, String page_size) {
        ParamOrderList paramOrderList = new ParamOrderList();
        paramOrderList.user_id = user_id;
        paramOrderList.status = status;
        paramOrderList.page = page + "";
        paramOrderList.page_size = page_size;
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
                                adapter = new Adapter<OrderModel>(getActivity(), R.layout.listview_item_order_zhu_lin_zhong) {
                                    @Override
                                    protected void convert(final AdapterHelper helper, final OrderModel orderModel) {
                                        final int position = helper.getPosition();
                                        helper.setImageUrl(R.id.iv_car_picture, orderModel.FImg)
                                                .setText(R.id.tv_order_number, "订单号:" + orderModel.PID)
                                                .setText(R.id.tv_show_qu_che_shop, orderModel.FShopName)
                                                .setText(R.id.tv_show_crete_date, orderModel.TotalAmount)
                                                .setText(R.id.tv_show_qu_che_time, orderModel.FStartTime);
                                        if (orderModel.FIsBack.equals("1")) {
                                            //立即还车
                                            helper.setImageResource(R.id.iv_quan_e_zhi_fu,R.mipmap.li_ji_huan_che);
                                            helper.getView(R.id.iv_quan_e_zhi_fu).setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                      JLog.w("申请还车");
                                                    show_huanche_dialog(orderModel.PID);
                                                }
                                            });
                                        }else{
                                            //已经还车
                                            helper.setImageResource(R.id.iv_quan_e_zhi_fu,R.mipmap.dengdaichuli);
                                            helper.getView(R.id.iv_quan_e_zhi_fu).setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    JLog.w("等待处理");
                                                }
                                            });
                                        }

                                    }
                                };
                                adapter.addAll(base.data);
                                plv_order_zu_lin_zhong_list.setAdapter(adapter);
                            } else {
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



    //show 还车 对话框
    public void show_huanche_dialog(final String order_id) {
        mAlertView = new AlertView("确定归该车吗?", null, null, new String[]{"取消", "确定"}, null, getActivity(),
                AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                JLog.w(position + "");
                if (position == 0) {
                    JLog.w("取消");
                } else if (position == 1) {
                    JLog.w("确定");
                    huan_che(order_id);
                }
            }
        });
        mAlertView.show();
    }

    //申请还车
    public void huan_che(final String order_id) {

        ParamBackCar paramBackCar = new ParamBackCar();
        paramBackCar.order_id = order_id;
        Callback.Cancelable cancelable
                = x.http().post(paramBackCar, new Callback.CommonCallback<String>() {
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
                UtilsJson.printJsonData(result);
                Gson gson = new Gson();
                Type type = new TypeToken<JsonBase2<List<SetOrderModel>>>() {
                }.getType();
                JsonBase2<List<SetOrderModel>> base = gson
                        .fromJson(result, type);
                if (!base.status.toString().trim().equals("0")) {
                    if (base.data != null) {
                        JLog.w("申请还车成功");
                        Utils.ShowText(getActivity(), "申请还车成功");
                        adapter.clear();
                        prl_prl_02.autoRefresh();
                    }
                } else {
                    JLog.w("删除订单失败");
                }

            }
        });

    }

}
