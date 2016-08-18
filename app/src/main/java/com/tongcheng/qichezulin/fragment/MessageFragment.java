package com.tongcheng.qichezulin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;


import com.flyco.tablayout.SegmentTabLayout;
import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;



/**
 * Created by 林尧 on 2016/8/18.
 */

@ContentView(R.layout.fragment_message)
public class MessageFragment extends PuTongFragment {


    @ViewInject(R.id.tl_4)
    SegmentTabLayout tl_4;
    private String[] mTitles_2 = {"订单消息", "系统消息"};

    @Override
    void setClickListenerOnView() {

    }

    @Override
    void initData() {

    }

    @Override
    void initView() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tl_4.setTabData(mTitles_2);
        tl_4.showDot(1);

    }

    @Override
    public void onStop() {
        super.onStop();
        JLog.w("onStop");

    }

    @Override
    public void onPause() {
        super.onPause();
        JLog.w("onPause");

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        JLog.w("onDestroy");

    }

    @Override
    public void onResume() {
        super.onResume();
        JLog.w("onResume");
    }
}
