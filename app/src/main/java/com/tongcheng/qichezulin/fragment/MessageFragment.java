package com.tongcheng.qichezulin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;

import com.flyco.tablayout.SegmentTabLayout;
import com.tongcheng.qichezulin.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by 林尧 on 2016/8/18.
 */

@ContentView(R.layout.fragment_message)
public class MessageFragment extends PuTongFragment {


    @ViewInject(R.id.fl_change)
    FrameLayout fl_change;
    @ViewInject(R.id.tl_4)
    SegmentTabLayout tl_4;
    private ArrayList<Fragment> fragments = new ArrayList<>();
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
        fragments.add(new OrderMessageFragment());
        fragments.add(new SystemMessageFragment());
        tl_4.setTabData(mTitles_2, getActivity(), R.id.fl_change, fragments);
        tl_4.showDot(1);
    }
}
