package com.tongcheng.qichezulin.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.flyco.tablayout.SegmentTabLayout;
import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.fragment.FaPiaoTaiTouFragment;
import com.tongcheng.qichezulin.fragment.PeiSongAddressFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 林尧 on 2016/8/18.
 */

@ContentView(R.layout.activity_my_fa_piao)
public class MyFaPiaoctivity extends PuTongFragmentActivity2 {

    //

    @ViewInject(R.id.tl_4)
    SegmentTabLayout tl_4;
    ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private String[] mTitles_2 = {"发票抬头", "配送地址"};

    @Override
    void initData() {
        fragments.add(new FaPiaoTaiTouFragment());
        fragments.add(new PeiSongAddressFragment());
    }

    @Override
    void initView() {
        tv_first.setVisibility(View.VISIBLE);
        tv_first.setText("我的发票");
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
        tl_4.setTabData(mTitles_2, this, R.id.fl_change, fragments);

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