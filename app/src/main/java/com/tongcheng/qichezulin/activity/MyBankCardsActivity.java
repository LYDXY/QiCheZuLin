package com.tongcheng.qichezulin.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.pulltorefresh.PullToRefreshLayout;
import com.tongcheng.qichezulin.pulltorefresh.PullableListView;
import com.tongcheng.qichezulin.utils.UtilsTiaoZhuang;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;

/**
 * Created by 林尧 on 2016/8/10.
 */

@ContentView(R.layout.activity_my_bank_cards)
public class MyBankCardsActivity extends PuTongActivity2 {


    @ViewInject(R.id.ib_add_card)
    ImageButton ib_add_card;

    @Override
    void initData() {

    }

    @Override
    void initView() {
        ib_add_card.setOnClickListener(this);
        tv_first.setVisibility(View.VISIBLE);
        tv_first.setText("我的银行卡");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_return:
                onBackPressed();
                break;
            case R.id.ib_add_card:
                UtilsTiaoZhuang.ToAnotherActivity(MyBankCardsActivity.this, AddBankCardActivity.class);
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
        JLog.i("onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JLog.i("onDestroy");
    }
}
