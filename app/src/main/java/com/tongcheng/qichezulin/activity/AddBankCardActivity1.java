package com.tongcheng.qichezulin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tongcheng.lqs.listener.Etlistener_textwatcher;
import com.tongcheng.lqs.network.Network_lqs;
import com.tongcheng.lqs.staticdata.Staticdata;
import com.tongcheng.lqs.utils.Log_lqs;
import com.tongcheng.qichezulin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * Created by 林尧 on 2016/7/22.
 */


@ContentView(R.layout.activity_add_bank_card1)
public class AddBankCardActivity1 extends Activity implements View.OnClickListener {

    private String name="";
    private String bankcardnumber="";
    private String bankname="";

    private Network_lqs network_lqs_next;


    @ViewInject(R.id.tv_first) //标题
            TextView tv_first;

    @ViewInject(R.id.iv_return) //回退
            ImageView iv_return;

    @ViewInject(R.id.tv_a_add_bank_card1_next) //tv确定
            TextView tv_a_add_bank_card1_next;

    @ViewInject(R.id.et_a_add_bank_card1_phonenumber) //et手机号
            EditText et_a_add_bank_card1_phonenumber;

    @ViewInject(R.id.iv_a_add_bank_card1_cancelphonenumber) //iv取消手机号
            ImageView iv_a_add_bank_card1_cancelphonenumber;


    private void init_lqs() {
        Intent intent = getIntent();
        name=intent.getStringExtra("name");
        bankcardnumber=intent.getStringExtra("bankcardnumber");
        bankname=intent.getStringExtra("bankname");

        et_a_add_bank_card1_phonenumber.addTextChangedListener(new Etlistener_textwatcher(et_a_add_bank_card1_phonenumber) {
            @Override
            public void callback_empty() {
                iv_a_add_bank_card1_cancelphonenumber.setVisibility(View.GONE);
            }

            @Override
            public void callback_notempty() {
                iv_a_add_bank_card1_cancelphonenumber.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initView();
        init_lqs();
    }

    public void initView() {
        tv_first.setVisibility(View.VISIBLE);
        tv_first.setText("添加银行卡");
        tv_first.setOnClickListener(this);
        iv_return.setOnClickListener(this);
        iv_a_add_bank_card1_cancelphonenumber.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_return:
                onBackPressed();
                break;
            case R.id.tv_a_add_bank_next:
//                if (et_a_add_bank_card_name.length() <= 0) {
//                    Toast.makeText(this, "请输入持卡人姓名", Toast.LENGTH_LONG).show();
//                }
//                if (et_a_add_bank_card_name.length() <= 0) {
//                    Toast.makeText(this, "请输入银行卡号", Toast.LENGTH_SHORT).show();
//                } else {
//                    RequestParams requestParams = new RequestParams(Staticdata.url_getbankcardname);
//                    requestParams.addBodyParameter("cardnumber", et_a_add_bank_card_number.getText().toString());
//                    network_lqs_next.post_jsonobject(requestParams);
//                }
              break;
        }

    }
}
