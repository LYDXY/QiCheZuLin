package com.tongcheng.qichezulin.activity;

import android.os.Bundle;
import android.view.View;
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
import com.tongcheng.qichezulin.model.CarModel3;
import com.tongcheng.qichezulin.model.FuWuModel;
import com.tongcheng.qichezulin.model.JsonBase2;
import com.tongcheng.qichezulin.utils.UtilsJson;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by 林尧 on 2016/8/18.
 */

@ContentView(R.layout.activity_yu_yue)
public class YuYueActivity extends PuTongActivity2 {


    @ViewInject(R.id.lv_fu_wu)
    ListView lv_fu_wu;
    @ViewInject(R.id.iv_car_picture)
    ImageView iv_car_picture;
    @ViewInject(R.id.tv_car_name)
    TextView tv_car_name;
    @ViewInject(R.id.tv_car_remark)
    TextView tv_car_remark;
    private Adapter<FuWuModel> adapter;
    private CarModel3 carModel3;
    private String days;

    @Override
    void initData() {
        carModel3 = (CarModel3) getIntent().getSerializableExtra("obj");
        days = getIntent().getExtras().getString("days");
        getFuWu();
        x.image().bind(iv_car_picture, carModel3.FImg);
        tv_car_name.setText(carModel3.FCarName);
        tv_car_remark.setText(carModel3.FRemark);

    }

    @Override
    void initView() {
        tv_first.setVisibility(View.VISIBLE);
        tv_first.setText("提交预约");


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
                            adapter = new Adapter<FuWuModel>(YuYueActivity.this, R.layout.listview_item_fu_wu) {
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

    }

}
