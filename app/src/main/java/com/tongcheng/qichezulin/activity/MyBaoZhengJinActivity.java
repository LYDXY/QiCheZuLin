package com.tongcheng.qichezulin.activity;

import android.os.Bundle;
import android.view.View;

import com.tongcheng.qichezulin.R;

import org.xutils.view.annotation.ContentView;

/**
 * Created by 林尧 on 2016/8/18.
 */

@ContentView(R.layout.activity_my_bao_zheng_jin)
public class MyBaoZhengJinActivity extends PuTongActivity2 {
    @Override
    void initData() {

    }

    @Override
    void initView() {
        tv_first.setVisibility(View.VISIBLE);
        tv_first.setText("保证金");
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
}
