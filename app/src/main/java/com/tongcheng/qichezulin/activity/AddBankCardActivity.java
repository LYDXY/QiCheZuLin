package com.tongcheng.qichezulin.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.tongcheng.qichezulin.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * Created by 林尧 on 2016/7/22.
 */


@ContentView(R.layout.activity_add_bank_card)
public class AddBankCardActivity extends Activity {


    @ViewInject(R.id.tv_first) //标题
            TextView tv_first;

    @ViewInject(R.id.iv_return) //回退
            ImageView iv_return;

    @ViewInject(R.id.et_chi_ka_ren) //持卡人姓名
            EditText et_chi_ka_ren;

    @ViewInject(R.id.et_input_number) //卡号
            EditText et_input_number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }
}
