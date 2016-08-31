package com.tongcheng.qichezulin.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiongbull.jlog.JLog;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.tongcheng.qichezulin.Adapter.CarTypeAdapter;
import com.tongcheng.qichezulin.Param.ParamGetCarType;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.model.CarTypeModel;
import com.tongcheng.qichezulin.model.JsonBase;
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
import java.util.ArrayList;

/**
 * Created by 林尧 on 2016/8/3.
 */
@ContentView(R.layout.activity_chose)
public class ChoseActivity extends PuTongActivity2 {

    public static  String min="";
    public static  String max="";
    public static   String carType="";

    @ViewInject(R.id.radioGroup)
    RadioGroup radioGroup;


    @ViewInject(R.id.lv_hao_do_you_chose)
    ListView lv_hao_do_you_chose;
    private CarTypeAdapter adapter;

    @ViewInject(R.id.tl_4)
    SegmentTabLayout tl_4;
    private String[] mTitles_2 = {"按价格", "按车型"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListenerOnView();
        initData();
        initView();
    }

    @Override
    void initData() {
        get_carType();
    }

    @Override
    void initView() {
        tv_first.setText("确定");
        tv_first.setVisibility(View.VISIBLE);
        tl_4.setTabData(mTitles_2);
        tl_4.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                JLog.w(position + "");
                if (position == 0) {
                    radioGroup.setVisibility(View.VISIBLE);
                    lv_hao_do_you_chose.setVisibility(View.GONE);
                } else if (position == 1) {
                    radioGroup.setVisibility(View.GONE);
                    lv_hao_do_you_chose.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        //单选
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //获取变更后的选中项的ID
                int radioButtonId = radioGroup.getCheckedRadioButtonId();
                //根据ID获取RadioButton的实例
                RadioButton rb = (RadioButton) ChoseActivity.this.findViewById(radioButtonId);
                switch (rb.getId()) {
                    case R.id.radio_first:
                        JLog.w("价格不限");
                        break;
                    case R.id.radio_second:
                        JLog.w("150");
                        min="0";
                        max="150";
                        break;
                    case R.id.radio_three:
                        JLog.w("300");
                        min="150";
                        max="300";
                        break;
                    case R.id.radio_fourth:
                        JLog.w("500");
                        min="300";
                        max="500";
                        break;
                    case R.id.radio_fifth:
                        break;
                }


            }
        });
        lv_hao_do_you_chose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                JLog.w("点击的位置=" + i);
                JLog.w("选中的位置=" + lv_hao_do_you_chose.getCheckedItemPosition() + "");   // 即获取选中位置);
                JLog.w(adapter.getItem(lv_hao_do_you_chose.getCheckedItemPosition()).FTypeName);
                carType=adapter.getItem(lv_hao_do_you_chose.getCheckedItemPosition()).PID;

            }
        });
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
    protected void onResume() {
        super.onResume();
        JLog.w("onResume");

    }

    //获取车的类型
    public void get_carType(){
        ParamGetCarType paramGetCarType = new ParamGetCarType();
        Callback.Cancelable cancelable
                = x.http().post(paramGetCarType, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                UtilsJson.printJsonData(result);
                Gson gson = new Gson();
                Type type = new TypeToken<JsonBase<ArrayList<CarTypeModel>>>() {
                }.getType();
                JsonBase<ArrayList<CarTypeModel>> base = gson
                        .fromJson(result, type);
                if (!base.status.toString().trim().equals("0")) {
                    if (base.data.size() > 0) {

                        if (adapter == null) {
                            adapter = new CarTypeAdapter(ChoseActivity.this, base.data);
                            lv_hao_do_you_chose.setAdapter(adapter);

                        }
                    } else {
                        JLog.w(base.info);

                    }

                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
