package com.tongcheng.qichezulin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jiongbull.jlog.JLog;
import com.tongcheng.lqs.staticdata.Staticid;
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

    @ViewInject(R.id.prl_first) //昵称
            PercentRelativeLayout prl_first;

    @ViewInject(R.id.prl_second) //绑定手机号码
            PercentRelativeLayout prl_second;

    @ViewInject(R.id.prl_band_card) //绑定银行卡
            PercentRelativeLayout prl_band_card;

    @ViewInject(R.id.prl_set_pass) //密码设置
            PercentRelativeLayout prl_set_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListenerOnView();
        initData();
        initView();
        init_lqs();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (1 == resultCode) {
            switch (requestCode) {
                case Staticid.AccountActivity_UpdateAccountUserNameActivity:
                    String name = data.getStringExtra("name");
                    tv_user_ccount.setText(name);
                    break;
                default:
                    break;
            }
        }
    }

    private void init_lqs() {
        tv_show_phone_number.setText(UtilsString.hidePhoneNumber(UtilsUser.get_user_phoen(AccountActivity.this)));
        tv_user_ccount.setText(UtilsUser.get_user_name(AccountActivity.this));
    }

    @Override
    void initData() {

    }

    @Override
    void initView() {
        prl_first.setOnClickListener(this);
        prl_second.setOnClickListener(this);
        prl_band_card.setOnClickListener(this);
        prl_set_pass.setOnClickListener(this);
        tv_first.setText("账户信息");
        tv_first.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                onBackPressed();
                break;
            case R.id.prl_first:
                startActivityForResult(new Intent(AccountActivity.this, UpdateAccountUserNameActivity.class), Staticid.AccountActivity_UpdateAccountUserNameActivity);
                break;
            case R.id.prl_band_card: //绑定银卡模块
                UtilsTiaoZhuang.ToAnotherActivity(AccountActivity.this, MyBankCardsActivity.class);
                break;
            case R.id.prl_second: //绑定手机号码
                UtilsTiaoZhuang.ToAnotherActivity(AccountActivity.this, BindPhoneNumberActivity.class);
                break;
            case R.id.prl_set_pass:
                UtilsTiaoZhuang.ToAnotherActivity(AccountActivity.this, SetPasswordActivity.class);
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        JLog.w("onResume");
    }
}
