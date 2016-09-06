package com.tongcheng.qichezulin.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.Param.ParamGetUserMoney;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.model.JsonBase2;
import com.tongcheng.qichezulin.model.MoneyModel;
import com.tongcheng.qichezulin.utils.UtilsJson;
import com.tongcheng.qichezulin.utils.UtilsString;
import com.tongcheng.qichezulin.utils.UtilsTiaoZhuang;
import com.tongcheng.qichezulin.utils.UtilsUser;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by 林尧 on 2016/8/18.
 */

@ContentView(R.layout.activity_my_wallect)
public class MyWallectActivity extends PuTongActivity {


    @ViewInject(R.id.tv_show_my_money)
    TextView tv_show_my_money;
    @ViewInject(R.id.rl_a_balanceinstruction)
    PercentRelativeLayout rl_a_balanceinstruction;
    @ViewInject(R.id.rl_a_accountsafe)
    PercentRelativeLayout rl_a_accountsafe;
    @ViewInject(R.id.rl_a_balanceget)
    PercentRelativeLayout rl_a_balanceget;
    @ViewInject(R.id.rl_a_rechange)
    PercentRelativeLayout rl_a_rechange;

    @Override
    void initData() {
        rl_a_balanceinstruction.setOnClickListener(this);
        rl_a_accountsafe.setOnClickListener(this);
        rl_a_balanceget.setOnClickListener(this);
        rl_a_rechange.setOnClickListener(this);
    }

    @Override
    void initView() {
        tv_first.setVisibility(View.VISIBLE);
        tv_first.setText("我的钱包");
        tv_second.setText("余额明细");
        tv_second.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                onBackPressed();
                break;
            case R.id.tv_second:
                JLog.i("余额明细");
                break;
            case R.id.rl_a_balanceinstruction:
                UtilsTiaoZhuang.ToAnotherActivity(this, BalanceinstructionActivity.class);
                break;
            case R.id.rl_a_accountsafe:
                UtilsTiaoZhuang.ToAnotherActivity(this, AccountsafeActivity.class);
                break;
            case R.id.rl_a_balanceget:
                UtilsTiaoZhuang.ToAnotherActivity(this, BalancegetActivity.class);
                break;
            case R.id.rl_a_rechange:
                UtilsTiaoZhuang.ToAnotherActivity(this, RechangeActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
        setListenerOnView();
//        if (UtilsUser.getUser(this).PID != null && !UtilsUser.getUser(this).PID.equals("")) {
//            get_now_my_money(UtilsUser.getUser(this).PID);
//        }

    }

    //获取现在的个人钱包金额
    public void get_now_my_money(String user_id) {
        ParamGetUserMoney paramGetUserMoney = new ParamGetUserMoney();
        paramGetUserMoney.user_id = user_id;
        Callback.Cancelable cancelable
                = x.http().post(paramGetUserMoney, new Callback.CommonCallback<String>() {
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
                    Type type = new TypeToken<JsonBase2<List<MoneyModel>>>() {
                    }.getType();
                    JsonBase2<List<MoneyModel>> base = gson
                            .fromJson(result, type);
                    if (!base.status.toString().trim().equals("0")) {
                        if (base.data != null) {
                            JLog.w("获取用户余额成功");
                            tv_show_my_money.setText("¥" + UtilsString.strToFloat2(Float.valueOf(base.data.get(0).FMoney), "0.00"));
                        }
                    } else {
                        JLog.w("获取用户余额失败");
                    }
                } catch (Exception E) {
                    E.printStackTrace();
                }

            }
        });
    }
}
