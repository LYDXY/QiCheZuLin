package com.tongcheng.qichezulin.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * Created by 林尧 on 2016/7/22.
 */


@ContentView(R.layout.activity_add_bank_card)
public class AddBankCardActivity extends Activity implements View.OnClickListener {


    @ViewInject(R.id.tv_first) //标题
            TextView tv_first;

    @ViewInject(R.id.iv_return) //回退
            ImageView iv_return;

    @ViewInject(R.id.et_chi_ka_ren) //持卡人姓名
            EditText et_chi_ka_ren;

    @ViewInject(R.id.et_input_number) //卡号
            EditText et_input_number;

    @ViewInject(R.id.ib_next_step) //下一步
            ImageButton ib_next_step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initView();
    }

    public void initView() {
        tv_first.setVisibility(View.VISIBLE);
        tv_first.setText("添加银行卡");
        tv_first.setOnClickListener(this);
        iv_return.setOnClickListener(this);
        ib_next_step.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_return:
                onBackPressed();
                break;
            case R.id.ib_next_step:
                JLog.i("下一步");
                break;
        }

    }
}
