package com.tongcheng.qichezulin.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.pulltorefresh.PullToRefreshLayout;
import com.tongcheng.qichezulin.pulltorefresh.PullableListView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;

/**
 * Created by 林尧 on 2016/8/10.
 */

@ContentView(R.layout.activity_find_car_type)
public class FindCarTypeActivity extends PuTongActivity2 {


    @ViewInject(R.id.prl_prl) //下拉刷新控件
            PullToRefreshLayout prl_prl;
    @ViewInject(R.id.plv_car_list)
    PullableListView plv_car_list; //listview
    private boolean isFirstIn = true;

    @Override
    void initData() {

    }

    @Override
    void initView() {
        tv_first.setVisibility(View.VISIBLE);
        tv_first.setText("选择车型");
        try {
            prl_prl.setGifRefreshView(new GifDrawable(getResources(), R.mipmap.anim));
            prl_prl.setGifLoadmoreView(new GifDrawable(getResources(), R.mipmap.anim));

        } catch (Resources.NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
        initView();
        setListenerOnView();
        setOnPullListenerOnprl_prl();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // 第一次进入自动刷新
        if (isFirstIn) {
            prl_prl.autoRefresh();
            isFirstIn = false;
        }
    }

    public void setOnPullListenerOnprl_prl() {
        prl_prl.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        });
    }

}