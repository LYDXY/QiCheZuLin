package com.tongcheng.qichezulin.activity;

import android.os.Bundle;
import android.view.View;

import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.R;

import org.xutils.view.annotation.ContentView;

/**
 * Created by 林尧 on 2016/8/10.
 */

@ContentView(R.layout.activity_setpassword)
public class SetPasswordActivity extends PuTongActivity {







    @Override
    void initData() {


    }

    @Override
    void initView() {

        tv_first.setVisibility(View.VISIBLE);
        tv_second.setVisibility(View.VISIBLE);
        tv_first.setText("设置密码");
        tv_second.setText("完成");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_return:
                onBackPressed();
                break;
            case R.id.tv_second:

                break;
            case R.id.tv_user_this_car:
              
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
