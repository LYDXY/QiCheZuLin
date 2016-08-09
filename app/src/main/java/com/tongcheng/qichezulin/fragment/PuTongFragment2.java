package com.tongcheng.qichezulin.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tongcheng.qichezulin.pulltorefresh.PullToRefreshLayout;

import org.xutils.x;

/**
 * Created by 林尧 on 2016/7/22. 带上下拉控件的 片段 ,并且实现了 监听
 */
public abstract class PuTongFragment2 extends Fragment implements View.OnClickListener, PullToRefreshLayout.OnPullListener {

    // 第一次进入自动刷新
    public boolean isFirstIn = true;
    private boolean injected = false;

    //给上下啦 实现监听
    abstract void setPullListenerOnPullToRefreshLayout();

    // 给其他的控件设置点击监听
    abstract void setOnClickListenerOnView();

    abstract void initData();

    abstract void initView();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
        return x.view().inject(this, inflater, container);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
    }


}
