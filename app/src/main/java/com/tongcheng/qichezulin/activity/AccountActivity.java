package com.tongcheng.qichezulin.activity;

import android.os.Bundle;
import android.view.View;

import com.tongcheng.qichezulin.R;

import org.xutils.view.annotation.ContentView;

/**
 * Created by 林尧 on 2016/8/3.
 */
@ContentView(R.layout.activity_account)
public class AccountActivity extends PuTongActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListenerOnView();
        initData();
        initView();
    }

    @Override
    void initData() {

    }

    @Override
    void initView() {
        tv_first.setText("账户信息");
        tv_first.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                onBackPressed();
                break;
        }
    }
}
