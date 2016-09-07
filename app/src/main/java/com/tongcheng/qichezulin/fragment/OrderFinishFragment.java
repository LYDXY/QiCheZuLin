package com.tongcheng.qichezulin.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiongbull.jlog.JLog;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.tongcheng.qichezulin.Param.ParamDeleteOrder;
import com.tongcheng.qichezulin.Param.ParamOrderList;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.model.JsonBase2;
import com.tongcheng.qichezulin.model.OrderModel;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 林尧 on 2016/8/18.
 */

@ContentView(R.layout.fragment_order_finish)
public class OrderFinishFragment extends Fragment {

    public Adapter adapter;
    Map<OrderModel, Boolean> delete_BooleanHashMap = new HashMap<OrderModel, Boolean>(); //记录要删除的对象

    @ViewInject(R.id.buuuuuuu)
    Button button;
    //===============================end
    String user_id = "";
    String token = "";
    String status = "3";
    int page = 1;
    String page_size = "10";
    @ViewInject(R.id.prl_prl_03) //上下拉控件
            PullToRefreshLayout prl_prl_03;
    @ViewInject(R.id.plv_order_finish_list)
    PullableListView plv_order_finish_list; //list 控件
    private TextView tv_second; //编辑按钮
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
        user_id=UtilsUser.getUserID(getActivity());
        final UtilsUser utilsUser = new UtilsUser(getActivity());
        token = utilsUser.getToken_lqs();
        if (!token.equals("")) {
            //获取成功
            JLog.w("token获取成功");
            get_order_yu_yue_list(user_id, status, page, page_size,token);
        } else {
            utilsUser.init_Callback_getToken(new UtilsUser.Callback_getToken() {
                @Override
                public void start() {
                    //tokentime超时，重网络获取
                    JLog.w("tokentime超时，重网络获取token,tokentime");
                    token = utilsUser.getToken_lqs();
                    JLog.w(token);
                    get_order_yu_yue_list(user_id, status, page, page_size,token);
                }
            });
        }
        tv_second = (TextView) getActivity().findViewById(R.id.tv_second);
        tv_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JLog.w("OrderFinishFragment");
                new AlertView(null, null, "取消", null,
                        new String[]{"删除"}, getActivity(), AlertView.Style.ActionSheet, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {

                        if (position != -1) {
                            JLog.w(adapter.getCount() + "");
                            List<OrderModel> orderModels = new ArrayList<OrderModel>(); //记录打勾的的子项
                            List<String> delete_id = new ArrayList<String>(); //记录要删除的ID
                            for (OrderModel key : delete_BooleanHashMap.keySet()) {
                                if (delete_BooleanHashMap.get(key)) {
                                    orderModels.add(key);
                                    delete_id.add(key.PID);
                                }
                            }
                            if (delete_id.size() > 0) {
                                StringBuilder StringBuilder = new StringBuilder();
                                for (int i = 0; i < delete_id.size(); i++) {
                                    if (i == (delete_id.size() - 1)) {
                                        StringBuilder.append(delete_id.get(i));
                                    } else {
                                        StringBuilder.append(delete_id.get(i)).append(",");
                                    }

                                }
                                JLog.w(StringBuilder.toString());
                                delete_orders(StringBuilder.toString(), orderModels);
                            }
                        }
                        // 进行删除操作 ,如果服务器返回成功 ,,,则再刷新界面
                        // adapter.removeAll(orderModels); 采用本地移除的发有 bug
                        // adapter.notifyDataSetChanged();

                    }
                }).show();
            }
        });


    }

    public void setOnPullListenerOnprl_prl() {
        prl_prl_03.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                page = 1;
                adapter.clear();
                get_order_yu_yue_list(user_id, status, page, page_size,token);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                page++;
                JLog.w("第几页:" + page);
                get_order_yu_yue_list(user_id, status, page, page_size,token);
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        });
    }

    //获取预约订单数据
    public void get_order_yu_yue_list(String user_id, String status, int page, String page_size,String token) {
        ParamOrderList paramOrderList = new ParamOrderList();
        paramOrderList.user_id = user_id;
        paramOrderList.status = status;
        paramOrderList.page = page + "";
        paramOrderList.page_size = page_size;
        paramOrderList.token=token;

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
                            JLog.w("获取已完成订单成功");
                            if (adapter == null) {
                                adapter = new Adapter<OrderModel>(getActivity(), R.layout.listview_item_order_finish) {
                                    @Override
                                    protected void convert(final AdapterHelper helper, final OrderModel orderModel) {
                                        final int position = helper.getPosition();
                                        helper.setImageUrl(R.id.iv_car_picture, orderModel.FImg)
                                                .setText(R.id.tv_order_number_show, orderModel.PID)
                                                .setText(R.id.tv_show_qu_che_shop, orderModel.FShopName)
                                                .setText(R.id.tv_show_qu_che_time, orderModel.FStartTime)
                                                .setText(R.id.tv_show_crete_date, orderModel.FCreateDate)
                                                .setImageResource(R.id.iv_is_qu_xiao_yu_yue, R.mipmap.zai_xian_tou_su);
                                        helper.getView(R.id.ck_is_choose).setVisibility(View.VISIBLE);
                                        CheckBox checkBox = helper.getView(R.id.ck_is_choose);
                                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                            @Override
                                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                JLog.w("...>>>>>>" + b);
                                                if (b) {
                                                    delete_BooleanHashMap.put(orderModel, true);

                                                } else {
                                                    delete_BooleanHashMap.put(orderModel, false);
                                                }
                                            }
                                        });
                                    }
                                };

                                adapter.addAll(base.data);
                                plv_order_finish_list.setAdapter(adapter);
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

    // 删除订单
    public void delete_orders(String del_ids, final  List<OrderModel> orderModels){

        ParamDeleteOrder paramDeleteOrder = new ParamDeleteOrder();
        paramDeleteOrder.del_ids=del_ids;
        Callback.Cancelable cancelable
                = x.http().post(paramDeleteOrder, new Callback.CommonCallback<String>() {
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
                    Type type = new TypeToken<JsonBase2<String>>() {
                    }.getType();
                    JsonBase2<String> base = gson
                            .fromJson(result, type);
                    if (!base.status.toString().trim().equals("0")) {
                        JLog.w("删除订单成功");
                        Utils.ShowText2(getActivity(),"删除订单成功");
                        //   adapter.removeAll(orderModels);// 采用本地移除的发有 bug
                        //   adapter.notifyDataSetChanged();
                        adapter.clear();
                        //   get_order_yu_yue_list(user_id, status, 1, page_size);
                        prl_prl_03.autoRefresh();
                    } else {
                        JLog.w("删除订单失败");
                        Utils.ShowText2(getActivity(),"删除订单失败");
                    }
                } catch (Exception E) {
                    E.printStackTrace();
                }

            }
        });
    }

}
