package com.tongcheng.qichezulin.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 林尧 on 2016/8/18.
 */

@ContentView(R.layout.fragment_order_qu_xiao)
public class OrderYiQuXiaoFragment extends Fragment {


    public Adapter adapter;
    Map<String, Boolean> delete_BooleanHashMap = new HashMap<String, Boolean>(); //记录要删除的id
    Map<Integer, Boolean> positon_BooleanHashMap = new HashMap<Integer, Boolean>(); //记录要移除的位置
    String user_id = "";
    String status = "3";
    int page = 1;
    String page_size = "10";
    @ViewInject(R.id.prl_prl_04) //上下拉控件
            PullToRefreshLayout prl_prl_04;
    @ViewInject(R.id.plv_order_qu_xiao_list)
    PullableListView plv_order_qu_xiao_list; //list 控件
    private TextView tv_third; //编辑按钮
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
        tv_third = (TextView) getActivity().findViewById(R.id.tv_third);
        tv_third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //在fg里面设置了点击
                JLog.w("OrderYiQuXiaoFragment");


                new AlertView(null, null, "取消", null,
                        new String[]{"删除"}, getActivity(), AlertView.Style.ActionSheet, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        JLog.w("列表的总个数" + delete_BooleanHashMap.size());
                        Iterator iter = delete_BooleanHashMap.keySet().iterator();
                        List<String> delete_id = new ArrayList<String>();
                        while (iter.hasNext()) {
                            Object key = iter.next();

                            Object val = delete_BooleanHashMap.get(key);
                            JLog.w("true / false ======" + val + "");
                            if (val.toString().equals("true")) {
                                JLog.w("要删除的订单" + key + "");
                                delete_id.add(key.toString());
                            }
                        }
                        Iterator iter2 = positon_BooleanHashMap.keySet().iterator();
                        while (iter2.hasNext()) {
                            Object key2 = iter2.next();

                            Object val2 = positon_BooleanHashMap.get(key2);
                            JLog.w("true / false ======" + val2 + "");
                            if (val2.toString().equals("true")) {
                                JLog.w("要删除的位置" + key2 + "");
                                adapter.removeAt(Integer.parseInt(key2 + ""));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).show();
            }
        });
    }

    public void setOnPullListenerOnprl_prl() {
        prl_prl_04.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
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
                            JLog.w("获取订单已取消成功");

                            if (adapter == null) {
                                adapter = new Adapter<OrderModel>(getActivity(), R.layout.listview_item_order_qu_xiao) {
                                    @Override
                                    protected void convert(final AdapterHelper helper, final OrderModel orderModel) {
                                        final int position = helper.getPosition();
                                        delete_BooleanHashMap.put(orderModel.PID, false);
                                        positon_BooleanHashMap.put(position, false);
                                        helper.setImageUrl(R.id.iv_car_picture, orderModel.FImg)
                                                .setText(R.id.tv_show_qu_che_shop, orderModel.FShopName)
                                                .setText(R.id.tv_show_qu_che_time, orderModel.FStartTime)
                                                .setText(R.id.tv_show_huan_che_time, orderModel.FEndTime);
                                        helper.getView(R.id.ck_is_choose).setVisibility(View.VISIBLE);
                                        CheckBox checkBox = helper.getView(R.id.ck_is_choose);
                                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                            @Override
                                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                JLog.w("...>>>>>>" + b);
                                                if (b) {
                                                    delete_BooleanHashMap.put(orderModel.PID, true);
                                                    positon_BooleanHashMap.put(position, true);
                                                    JLog.w(delete_BooleanHashMap.size() + "");
                                                    JLog.w(positon_BooleanHashMap.size() + "");
                                                } else {
                                                    delete_BooleanHashMap.put(orderModel.PID, false);
                                                    positon_BooleanHashMap.put(position, false);
                                                    JLog.w(delete_BooleanHashMap.size() + "");
                                                    JLog.w(positon_BooleanHashMap.size() + "");
                                                }
                                            }
                                        });
                                    }
                                };

                                adapter.addAll(base.data);
                                plv_order_qu_xiao_list.setAdapter(adapter);
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
}
