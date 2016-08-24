package com.tongcheng.qichezulin.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.code19.library.SPUtils;
import com.code19.library.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiongbull.jlog.JLog;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.tongcheng.qichezulin.Param.ParamBili;
import com.tongcheng.qichezulin.Param.ParamGetExpense;
import com.tongcheng.qichezulin.Param.ParamUserJf;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.config.RootApp;
import com.tongcheng.qichezulin.model.BiLiModel;
import com.tongcheng.qichezulin.model.CarModel3;
import com.tongcheng.qichezulin.model.FuWuModel;
import com.tongcheng.qichezulin.model.JiFenModel;
import com.tongcheng.qichezulin.model.JsonBase2;
import com.tongcheng.qichezulin.utils.UtilsJson;
import com.tongcheng.qichezulin.utils.UtilsTiaoZhuang;
import com.tongcheng.qichezulin.utils.UtilsUser;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 林尧 on 2016/8/18.
 */

@ContentView(R.layout.activity_yu_yue)
public class YuYueActivity extends PuTongActivity2 {


    @ViewInject(R.id.iv_next_stats)
    Button iv_next_stats;//点击下一步
    @ViewInject(R.id.lv_fu_wu)
    ListView lv_fu_wu;
    @ViewInject(R.id.iv_car_picture)
    ImageView iv_car_picture;
    @ViewInject(R.id.tv_car_name)
    TextView tv_car_name;
    @ViewInject(R.id.tv_car_remark)
    TextView tv_car_remark;
    private String Jifen = "";
    private List<Float> floats = new ArrayList<>();
    private String bilizhi = null;
    private Adapter<FuWuModel> adapter;
    private CarModel3 carModel3;
    private String days;

    @Override
    void initData() {
        carModel3 = (CarModel3) getIntent().getSerializableExtra("obj");
        days = getIntent().getExtras().getString("days");
        getFuWu();
        x.image().bind(iv_car_picture, carModel3.FImg, RootApp.imageOptionsnew);
        tv_car_name.setText(carModel3.FCarName);
        tv_car_remark.setText(carModel3.FRemark);

    }

    @Override
    void initView() {
        tv_first.setVisibility(View.VISIBLE);
        tv_first.setText("提交预约");
        iv_next_stats.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.iv_return:
                onBackPressed();
                break;

            case R.id.iv_next_stats: //下一步
                Float Money = 0f;
                if (bilizhi != null && !bilizhi.equals("") && Jifen != null && !Jifen.equals("")) {
                    float bili = Float.parseFloat(bilizhi.trim()) / 100f;
                    JLog.w(bili + "");
                    if (floats.size() > 0) {
                        for (int i = 0; i < floats.size(); i++) {
                            JLog.w(floats.get(i) + "");
                            Money += floats.get(i);
                        }
                        //计算预付款
                        Money = Money * bili;
                        Bundle bundle = new Bundle();
                        bundle.putString("yufukuan", Money + "");
                        JLog.w("yufukuan" + Money);
                        JLog.w("用户的积分值" + Jifen);
                        bundle.putString("jifen", Jifen + "");
                        UtilsTiaoZhuang.ToAnotherActivity(YuYueActivity.this, OrderDetailActivity.class, bundle);
                    }
                } else {
                    JLog.w("数据有误");
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
        get_bi_li_value();
        get_jifen((String) UtilsUser.getSp(getApplication(), UtilsUser.KEY_USER_ID, ""));
    }


    //获取服务项目数据
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

                            for (int i = 0; i < base.data.size(); i++) {
                                if (base.data.get(i).FIsSingle.equals("true")) {
                                    floats.add(Float.parseFloat(base.data.get(i).FPrice.trim()));
                                } else {
                                    floats.add(Float.parseFloat(base.data.get(i).FPrice.trim()) * Float.parseFloat(days));
                                }
                            }



                            adapter = new Adapter<FuWuModel>(YuYueActivity.this, R.layout.listview_item_fu_wu) {
                                @Override
                                protected void convert(final AdapterHelper helper, final FuWuModel item) {

                                    if (item.FIsSingle.equals("true")) {
                                        helper.setText(R.id.tv_show_first, item.FName)
                                                .setText(R.id.tv_show_second, "¥" + item.FPrice + "×1")
                                                .setText(R.id.show_hao_much0, "¥" + item.FPrice);

                                    } else if (item.FIsSingle.equals("false")) {
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

    }


    //获取比例值
    public void get_bi_li_value() {
        ParamBili paramBili = new ParamBili();
        Callback.Cancelable cancelable
                = x.http().post(paramBili, new Callback.CommonCallback<String>() {
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
                    Type type = new TypeToken<JsonBase2<List<BiLiModel>>>() {
                    }.getType();
                    JsonBase2<List<BiLiModel>> base = gson
                            .fromJson(result, type);
                    if (!base.status.toString().trim().equals("0")) {
                        if (base.data != null) {
                            JLog.w("获取比例值成功");
                            JLog.w("比例值=" + base.data.get(0).FProportion);
                            bilizhi = base.data.get(0).FProportion;
                        }

                    } else {
                        JLog.w("获取比例值失败");
                    }
                } catch (Exception E) {
                    E.printStackTrace();
                }

            }
        });
    }


    //获取用户的积分
    public void get_jifen(String user_id) {
        ParamUserJf paramUserJf = new ParamUserJf();
        paramUserJf.user_id = user_id;
        Callback.Cancelable cancelable
                = x.http().post(paramUserJf, new Callback.CommonCallback<String>() {
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
                    Type type = new TypeToken<JsonBase2<List<JiFenModel>>>() {
                    }.getType();
                    JsonBase2<List<JiFenModel>> base = gson
                            .fromJson(result, type);
                    if (!base.status.toString().trim().equals("0")) {
                        if (base.data != null) {
                            JLog.w("获取积分成功");
                            JLog.w("积分值=" + base.data.get(0).FJiFen);
                            Jifen = base.data.get(0).FJiFen;
                        }

                    } else {
                        JLog.w("获取积分值失败");
                    }
                } catch (Exception E) {
                    E.printStackTrace();
                }

            }
        });
    }

}
