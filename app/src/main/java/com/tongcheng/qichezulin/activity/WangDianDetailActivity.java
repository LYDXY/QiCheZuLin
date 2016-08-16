package com.tongcheng.qichezulin.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.utils.UtilsTiaoZhuang;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by 林尧 on 2016/8/10.
 */

@ContentView(R.layout.activity_wand_dian_detail)
public class WangDianDetailActivity extends PuTongActivity0 {


    @Override
    void initData() {


    }

    @Override
    void initView() {
        tv_first.setVisibility(View.VISIBLE);
        tv_first.setText("网点详情");

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_return:
                onBackPressed();
                break;
            case R.id.iv_set_up:
                JLog.w("分享");
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
        JLog.w("onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JLog.w("onDestroy");
    }
}
