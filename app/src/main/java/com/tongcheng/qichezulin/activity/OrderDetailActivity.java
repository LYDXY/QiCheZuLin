package com.tongcheng.qichezulin.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.Param.ParamGetUserMoney;
import com.tongcheng.qichezulin.Param.ParamSetOrder;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.config.AppConfig;
import com.tongcheng.qichezulin.model.CarModel3;
import com.tongcheng.qichezulin.model.JsonBase2;
import com.tongcheng.qichezulin.model.MoneyModel;
import com.tongcheng.qichezulin.model.SetOrderModel;
import com.tongcheng.qichezulin.utils.PayOrderInfoUtil2_0;
import com.tongcheng.qichezulin.utils.PayResult;
import com.tongcheng.qichezulin.utils.Utils;
import com.tongcheng.qichezulin.utils.UtilsJson;
import com.tongcheng.qichezulin.utils.UtilsString;
import com.tongcheng.qichezulin.utils.UtilsTiaoZhuang;
import com.tongcheng.qichezulin.utils.UtilsUser;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 林尧 on 2016/8/18.
 */

@ContentView(R.layout.activity_order_detail)
public class OrderDetailActivity extends PuTongActivity2 {

    private CarModel3 carModel3;
    private String dingjing;
    private Integer use_ji_fen = 0;//记录使用了多少积分;

    @ViewInject(R.id.tv_chose_pay_ways) //支付方式
            TextView tv_chose_pay_ways;


    @ViewInject(R.id.iv_is_sure)//确认下单按钮
            ImageView iv_is_sure;


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

    private String payType = "0";//支付类型

    @Override
    void initData() {

    }

    @Override
    void initView() {


        try {
            if (UtilsUser.getUser(this) != null) {
                get_now_my_money(UtilsUser.getUser(this).PID);
                tv_show_phone_number.setText(UtilsUser.getUser(this).FMobilePhone);
            }
            if (getIntent().getExtras().getString("yufukuan") != null) {
                tv_show_yu_fu_money.setText("¥" + getIntent().getExtras().getString("yufukuan"));
            }
            if (getIntent().getExtras().getString("jifen") != null) {
                Float diyong = Float.parseFloat(UtilsString.strToFloat2(Float.parseFloat(getIntent().getExtras().getString("yufukuan")) * 0.1f, "#"));
                if (diyong < Float.parseFloat(getIntent().getExtras().getString("jifen"))) {
                    tv_shou_ji_fen.setText("-¥" + diyong + "");
                    tv_show_pay_money.setText("还需支付 ¥" + UtilsString.strToFloat2((Float.parseFloat(getIntent().getExtras().getString("yufukuan")) - diyong), "0.00"));
                    dingjing = UtilsString.strToFloat2((Float.parseFloat(getIntent().getExtras().getString("yufukuan")) - diyong), "0.00");

                } else {
                    tv_shou_ji_fen.setText("积分不够抵用预付款的10%");
                    tv_show_pay_money.setText("还需支付 ¥" + UtilsString.strToFloat2(Float.parseFloat(getIntent().getExtras().getString("yufukuan")), "0.00"));
                    dingjing = UtilsString.strToFloat2((Float.parseFloat(getIntent().getExtras().getString("yufukuan"))), "#.00");
                }
                //使用了多少积分
                use_ji_fen = Integer.parseInt(diyong.toString());
            }


        } catch (Exception E) {

        }
        carModel3 = (CarModel3) getIntent().getSerializableExtra("car");
        tv_first.setVisibility(View.VISIBLE);
        tv_first.setText("确定订单");
        tv_chose_pay_ways.setOnClickListener(this);
        iv_is_sure.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.iv_return:
                onBackPressed();
                break;

            case R.id.tv_chose_pay_ways:
                alert_pay_ways();
                break;

            case R.id.iv_is_sure:
                if (UtilsUser.getUser(this).PID == null || UtilsUser.getUser(this).PID.equals("")) {
                    JLog.w("用户id不能为空");
                } else if (carModel3.PID == null || carModel3.PID.equals("")) {
                    JLog.w("车id不能为空");
                } else if (carModel3.KShopID == null || carModel3.KShopID.equals("")) {
                    JLog.w("店铺id不能为空");
                } else if (getIntent().getExtras().getString("end_time").trim().equals("") || getIntent().getExtras().getString("end_time") == null) {
                    JLog.w("结束时间不能为空");
                } else if (getIntent().getExtras().getString("start_time").trim().equals("") || getIntent().getExtras().getString("start_time") == null) {
                    JLog.w("开始时间不能为空");
                } else if (dingjing.equals("") || dingjing == null) {
                    JLog.w("定金不能为空,就是预付款");
                } else if (getIntent().getExtras().getString("all_money").trim().equals("") || getIntent().getExtras().getString("all_money") == null) {
                    JLog.w("总支付的金额不能为空");
                } else if (getIntent().getExtras().getString("sheng_yu").trim().equals("") || getIntent().getExtras().getString("sheng_yu") == null) {
                    JLog.w("剩余的金额不能为空");
                } else if (getIntent().getExtras().getString("fu_wu_id_list").trim().equals("") || getIntent().getExtras().getString("fu_wu_id_list") == null) {
                    JLog.w("服务项目id集合不能为空");
                } else if (getIntent().getExtras().getString("zu_lin_fei_yong").trim().equals("") || getIntent().getExtras().getString("zu_lin_fei_yong") == null) {
                    JLog.w("租赁费用不能为空");
                } else if (payType.equals("0")) {
                    JLog.w("没有选择支付类型");
                    Utils.ShowText2(OrderDetailActivity.this, "没有选择支付方式");
                } else {
                    JLog.w("可以提交订单");
                    JLog.w(payType);
                    if (payType.equals("1")) {
                        get_order_que_ding(UtilsUser.getUser(this).PID, carModel3.PID, carModel3.KShopID,
                                getIntent().getExtras().getString("start_time").trim(),
                                getIntent().getExtras().getString("end_time").trim(),
                                dingjing,
                                getIntent().getExtras().getString("all_money").trim(),
                                getIntent().getExtras().getString("sheng_yu").trim(),
                                use_ji_fen + "",
                                getIntent().getExtras().getString("fu_wu_id_list").trim(), "", "",
                                payType);
                    } else if (payType.equals("2")) {

                        try {
                            pay_to_zhi_fu_bao(carModel3.PID + "-1", carModel3.FCarName, "0.01");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    } else if (payType.equals("3")) {

                    }

                }
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


    //弹出选择支付方式的窗口

    public void alert_pay_ways() {

        //拓展窗口
        final AlertView alertView = new AlertView(null, null, null, null, null, this, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {

            }
        });
        ViewGroup extView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.alertview01, null);
        extView.findViewById(R.id.iv_yu_er_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tv_chose_pay_ways.setText("余额支付");
                payType = "1";
                alertView.dismiss();
            }
        });
        extView.findViewById(R.id.iv_zhi_fu_bao_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tv_chose_pay_ways.setText("支付宝");
                payType = "2";
                alertView.dismiss();
            }
        });
        extView.findViewById(R.id.iv_wei_xin_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tv_chose_pay_ways.setText("微信");
                payType = "3";
                alertView.dismiss();
            }
        });
        alertView.addExtView(extView);
        alertView.show();

    }


    //下单按钮的确定操作
    public void get_order_que_ding(String user_id, String car_id, String shop_id, String start_time, String end_time, String deposit, String money, String lessmoney, String jifen, String expenseid, String invoiceId, String invoiceaddressId, String paytype) {
        ParamSetOrder paramSetOrder = new ParamSetOrder();
        paramSetOrder.user_id = user_id;
        paramSetOrder.car_id = car_id;
        paramSetOrder.shop_id = shop_id;
        paramSetOrder.start_time = start_time;
        paramSetOrder.end_time = end_time;
        paramSetOrder.deposit = deposit;
        paramSetOrder.money = money;
        paramSetOrder.lessmoney = lessmoney;
        paramSetOrder.jifen = jifen;
        paramSetOrder.expenseid = expenseid;
        paramSetOrder.invoiceId = invoiceId;
        paramSetOrder.invoiceaddressId = invoiceaddressId;
        paramSetOrder.paytype = paytype;
        Callback.Cancelable cancelable
                = x.http().post(paramSetOrder, new Callback.CommonCallback<String>() {
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }


            @Override
            public void onFinished() {
//
            }

            @Override
            public void onSuccess(String result) {
                try {
                    UtilsJson.printJsonData(result);
                    Gson gson = new Gson();
                    Type type = new TypeToken<JsonBase2<List<SetOrderModel>>>() {
                    }.getType();
                    JsonBase2<List<SetOrderModel>> base = gson
                            .fromJson(result, type);
                    if (!base.status.toString().trim().equals("0")) {
                        if (base.data != null) {
                            JLog.w("插入订单成功");
                            Utils.ShowText2(OrderDetailActivity.this, "插入订单成功");
                            UtilsTiaoZhuang.ToAnotherActivity(OrderDetailActivity.this, MyOrderActivity.class);
                        }
                    } else {
                        JLog.w("插入订单失败");
                        Utils.ShowText2(OrderDetailActivity.this, "插入订单失败");
                    }
                } catch (Exception E) {
                    E.printStackTrace();
                }

            }
        });

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
                            tv_user_money.setText("¥" + UtilsString.strToFloat2(Float.valueOf(base.data.get(0).FMoney), "0.00"));
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



    //调用支付宝支付
    public void pay_to_zhi_fu_bao(String orderid, String ordertitle, String money) throws UnsupportedEncodingException {

        Map<String, String> params = PayOrderInfoUtil2_0.buildOrderParamMap(AppConfig.SELLER_ID, AppConfig.ZHI_FU_BAO_APPID, orderid, ordertitle, money);
        String orderParam = PayOrderInfoUtil2_0.buildOrderParam(params);
        String sign = PayOrderInfoUtil2_0.getSign(params, AppConfig.ZHI_FU_BAO_KEY);
        final String orderInfo = orderParam + "&" + sign;
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(OrderDetailActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                JLog.w("============"+result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private static final int SDK_PAY_FLAG = 1;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();

                    JLog.w(resultInfo);
                    JLog.w(resultStatus);
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(OrderDetailActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(OrderDetailActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        }

    };
}
