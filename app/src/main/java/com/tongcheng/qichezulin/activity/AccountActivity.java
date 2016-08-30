package com.tongcheng.qichezulin.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
@ContentView(R.layout.activity_account)
public class AccountActivity extends PuTongActivity {

    @ViewInject(R.id.tv_show_phone_number)
    TextView tv_show_phone_number;

    @ViewInject(R.id.tv_user_ccount)
    TextView tv_user_ccount;

    @ViewInject(R.id.prl_band_card) //绑定银行卡
            PercentRelativeLayout prl_band_card;

    @ViewInject(R.id.prl_first)
            PercentRelativeLayout prl_first;


    @ViewInject(R.id.prl_second) //绑定银行卡
    PercentRelativeLayout prl_second;


    @ViewInject(R.id.prl_set_pass) //绑定银行卡
    PercentRelativeLayout prl_set_pass;


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
        prl_first.setOnClickListener(this);
        prl_second.setOnClickListener(this);
        prl_set_pass.setOnClickListener(this);
        prl_band_card.setOnClickListener(this);
        tv_first.setText("账户信息");
        tv_first.setVisibility(View.VISIBLE);
        if (UtilsUser.getUser(this).FMobilePhone != null && !UtilsUser.getUser(this).FMobilePhone.equals("")) {
            tv_show_phone_number.setText(UtilsString.hidePhoneNumber(UtilsUser.getUser(this).FMobilePhone));
        }
        if (UtilsUser.getUser(this).FUserName != null && !UtilsUser.getUser(this).FUserName.equals("")) {
            tv_user_ccount.setText(UtilsUser.getUser(this).FUserName);
        }
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
            case R.id.prl_first:
                UtilsTiaoZhuang.ToAnotherActivity(AccountActivity.this, UpdateAccountUserNameActivity.class);
                break;
            case R.id.prl_second:

                break;
            case R.id.prl_set_pass:

                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        JLog.w("onResume");
        if (UpdateAccountUserNameActivity.new_user_name!=null && !UpdateAccountUserNameActivity.new_user_name.equals("")) {
            tv_user_ccount.setText(UpdateAccountUserNameActivity.new_user_name);
        }
    }
}
