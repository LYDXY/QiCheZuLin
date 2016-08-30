package com.tongcheng.qichezulin.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.utils.UtilsString;
import com.tongcheng.qichezulin.utils.UtilsTiaoZhuang;
import com.tongcheng.qichezulin.utils.UtilsUser;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by 林尧 on 2016/8/3.
 */
@ContentView(R.layout.activity_chose)
public class ChoseActivity extends PuTongActivity2 {


    @ViewInject(R.id.lv_hao_do_you_chose)
    ListView lv_hao_do_you_chose;

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

    }

    @Override
    void initView() {
        tv_first.setText("确定");
        tv_first.setVisibility(View.VISIBLE);
        tl_4.setTabData(mTitles_2);
        tl_4.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                JLog.w(position+"");
            }

            @Override
            public void onTabReselect(int position) {

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
}
