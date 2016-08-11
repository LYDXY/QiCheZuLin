package com.tongcheng.qichezulin.activity;

import android.os.Bundle;
import android.view.View;

import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.utils.UtilsTiaoZhuang;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by 林尧 on 2016/8/3.
 */
@ContentView(R.layout.activity_account)
public class AccountActivity extends PuTongActivity {


    @ViewInject(R.id.prl_band_card) //绑定银行卡
            PercentRelativeLayout prl_band_card;

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
        prl_band_card.setOnClickListener(this);
        tv_first.setText("账户信息");
        tv_first.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                onBackPressed();
                break;
            case R.id.prl_band_card: //绑定银卡模块
                UtilsTiaoZhuang.ToAnotherActivity(AccountActivity.this, MyBankCardsActivity.class);
                break;
        }
    }
}
