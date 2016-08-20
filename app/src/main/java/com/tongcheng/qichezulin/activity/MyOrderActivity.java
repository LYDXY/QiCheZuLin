package com.tongcheng.qichezulin.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
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
public class MyOrderActivity extends PuTongFragmentActivity implements OrderFinishFragment.ListenerOnOrderFinishFragment {


    //可以用来fg刷新activity 界面----------end
    @ViewInject(R.id.tv_third)
    TextView tv_third;
    @ViewInject(R.id.tl_4)
    SegmentTabLayout tl_4;
    ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private String[] mTitles_2 = {"预约中", "租赁中", "已完成", "已取消"};
    private OrderYuYueFragment yuYueFragment = new OrderYuYueFragment();
    private OrderZuLinZhongFragment zuLinZhongFragment = new OrderZuLinZhongFragment();
    private OrderFinishFragment finishFragment = new OrderFinishFragment();
    private OrderYiQuXiaoFragment quXiaoFragment = new OrderYiQuXiaoFragment();

    //可以用来fg刷新activity 界面----------begin
    @Override
    public void do_work() {
        JLog.w("=============/////////////");
    }

    @Override
    void initData() {
        fragments.add(yuYueFragment);
        fragments.add(zuLinZhongFragment);
        fragments.add(finishFragment);
        fragments.add(quXiaoFragment);
    }

    @Override
    void initView() {
        tv_first.setVisibility(View.VISIBLE);
        tv_first.setText("我的订单");
        tv_second.setText("编辑");
        tv_third.setText("编辑");
        tv_second.setVisibility(View.GONE);
        tv_third.setVisibility(View.GONE);
        tl_4.setTabData(mTitles_2, this, R.id.fl_change, fragments);
        tl_4.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position == 0) {
                    tv_second.setVisibility(View.GONE);
                    tv_third.setVisibility(View.GONE);
                } else if (position == 1) {
                    tv_second.setVisibility(View.GONE);
                    tv_third.setVisibility(View.GONE);
                } else if (position == 2) {
                    tv_second.setVisibility(View.VISIBLE);
                    tv_third.setVisibility(View.GONE);
                } else if (position == 3) {
                    tv_second.setVisibility(View.GONE);
                    tv_third.setVisibility(View.VISIBLE);

                }
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
