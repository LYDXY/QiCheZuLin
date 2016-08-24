package com.tongcheng.qichezulin.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiongbull.jlog.JLog;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.tongcheng.qichezulin.Param.ParamGetExpense;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.config.RootApp;
import com.tongcheng.qichezulin.model.CarModel3;
import com.tongcheng.qichezulin.model.FuWuModel;
import com.tongcheng.qichezulin.model.JsonBase2;
import com.tongcheng.qichezulin.model.UserModel;
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

@ContentView(R.layout.activity_order_detail)
public class OrderDetailActivity extends PuTongActivity2 {


    @ViewInject(R.id.tv_show_pay_money) //预付款
            TextView tv_show_pay_money;

    @ViewInject(R.id.tv_show_yu_fu_money) //预付款
            TextView tv_show_yu_fu_money;

    @ViewInject(R.id.tv_shou_ji_fen) //积分
            TextView tv_shou_ji_fen;

    @ViewInject(R.id.tv_user_money) //余额
            TextView tv_user_money;


    @ViewInject(R.id.tv_show_phone_number) //手机号码
            TextView tv_show_phone_number;


    @Override
    void initData() {

    }

    @Override
    void initView() {


        try {
            if (getIntent().getExtras().getString("yufukuan") != null) {
                tv_show_yu_fu_money.setText("¥" + getIntent().getExtras().getString("yufukuan"));
            }
            if (getIntent().getExtras().getString("jifen") != null) {
                Float diyong = Float.parseFloat(getIntent().getExtras().getString("yufukuan")) * 0.1f;
                if (diyong < Float.parseFloat(getIntent().getExtras().getString("jifen"))) {
                    tv_shou_ji_fen.setText("-" + diyong + "");
                    tv_show_pay_money.setText("还需支付 ¥" + (Float.parseFloat(getIntent().getExtras().getString("yufukuan")) - diyong));
                } else {
                    tv_shou_ji_fen.setText("积分不够抵用预付款的10%");
                    tv_show_pay_money.setText("还需支付 ¥" + getIntent().getExtras().getString("yufukuan"));
                }

            }
            if (UtilsUser.getUser(this) != null) {
                tv_user_money.setText("¥" + UtilsUser.getUser(this).FMoney);
                tv_show_phone_number.setText(UtilsUser.getUser(this).FMobilePhone);
            }

        } catch (Exception E) {

        }

        tv_first.setVisibility(View.VISIBLE);
        tv_first.setText("确定订单");


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.iv_return:
                onBackPressed();
                break;


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
        setListenerOnView();
    }

   /* //获取服务项目数据
    public void getFuWu() {

        ParamGetExpense paramGetExpense = new ParamGetExpense();
        Callback.Cancelable cancelable
                = x.http().post(paramGetExpense, new Callback.CommonCallback<String>() {
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
                    Type type = new TypeToken<JsonBase2<List<FuWuModel>>>() {
                    }.getType();
                    JsonBase2<List<FuWuModel>> base = gson
                            .fromJson(result, type);
                    if (!base.status.toString().trim().equals("0")) {
                        if (base.data != null) {
                            JLog.w("获取收费服务项目成功");
                            adapter = new Adapter<FuWuModel>(OrderDetailActivity.this, R.layout.listview_item_fu_wu) {
                                @Override
                                protected void convert(final AdapterHelper helper, FuWuModel item) {

                                    if (item.FIsSingle.equals("true")) {
                                        helper.setText(R.id.tv_show_first, item.FName)
                                                .setText(R.id.tv_show_second, "¥" + item.FPrice + "×1")
                                                .setText(R.id.show_hao_much0, "¥" + item.FPrice);
                                    } else {
                                        helper.setText(R.id.tv_show_first, item.FName)
                                                .setText(R.id.tv_show_second, "¥" + item.FPrice + "×" + days)
                                                .setText(R.id.show_hao_much0, "¥" + Float.parseFloat(item.FPrice.trim()) * Integer.parseInt(days));
                                    }


                                }

                            };
                            adapter.addAll(base.data);
                            lv_fu_wu.setAdapter(adapter);
                        }

                    } else {
                        JLog.w("获取收费服务项目成功失败");
                    }
                } catch (Exception E) {
                    E.printStackTrace();
                }

            }
        });

    }*/

}
