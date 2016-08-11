package com.tongcheng.qichezulin.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.utils.UtilsTiaoZhuang;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by 林尧 on 2016/8/10.
 */

@ContentView(R.layout.activity_car_detail)
public class CarDetailActivity extends PuTongActivity {


    @ViewInject(R.id.tv_user_this_car)
    TextView tv_user_this_car;

    @Override
    void initData() {

    }

    @Override
    void initView() {
        tv_user_this_car.setOnClickListener(this);
        tv_first.setVisibility(View.VISIBLE);
        tv_second.setVisibility(View.VISIBLE);
        tv_first.setText("车辆详情");
        tv_second.setText("更换条件");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_return:
                onBackPressed();
                break;
            case R.id.tv_second:
                JLog.i("租车界面");
                UtilsTiaoZhuang.ToAnotherActivity(CarDetailActivity.this, ZuCheActivity.class);
                break;
            case R.id.tv_user_this_car:
                JLog.i("租用这辆车");
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListenerOnView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        JLog.i("onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JLog.i("onDestroy");
    }
}
