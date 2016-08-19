package com.tongcheng.qichezulin.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.flyco.tablayout.SegmentTabLayout;
import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.fragment.FaPiaoTaiTouFragment;
import com.tongcheng.qichezulin.fragment.OrderFinishFragment;
import com.tongcheng.qichezulin.fragment.OrderYiQuXiaoFragment;
import com.tongcheng.qichezulin.fragment.OrderYuYueFragment;
import com.tongcheng.qichezulin.fragment.OrderZuLinZhongFragment;
import com.tongcheng.qichezulin.fragment.PeiSongAddressFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by 林尧 on 2016/8/18.
 */

@ContentView(R.layout.activity_my_order)
public class MyOrderActivity extends PuTongFragmentActivity {


    @ViewInject(R.id.tl_4)
    SegmentTabLayout tl_4;
    ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private String[] mTitles_2 = {"预约中", "租赁中", "已完成", "已取消"};

    @Override
    void initData() {
        fragments.add(new OrderYuYueFragment());
        fragments.add(new OrderZuLinZhongFragment());
        fragments.add(new OrderFinishFragment());
        fragments.add(new OrderYiQuXiaoFragment());
    }

    @Override
    void initView() {
        tv_first.setVisibility(View.VISIBLE);
        tv_first.setText("我的订单");
        tv_second.setText("编辑");
        tv_second.setVisibility(View.VISIBLE);
        tl_4.setTabData(mTitles_2, this, R.id.fl_change, fragments);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                onBackPressed();
                break;
            case R.id.tv_second:
                JLog.w("点击了编辑");
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
