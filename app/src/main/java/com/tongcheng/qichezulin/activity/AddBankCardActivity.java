package com.tongcheng.qichezulin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.tongcheng.lqs.listener.Etlistener_textwatcher;
import com.tongcheng.lqs.network.Network_lqs;
import com.tongcheng.lqs.staticdata.Staticdata;
import com.tongcheng.lqs.staticdata.Staticid;
import com.tongcheng.qichezulin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * Created by lqs on 2016.9.6
 */

@ContentView(R.layout.activity_add_bank_card)
public class AddBankCardActivity extends Activity implements View.OnClickListener {

    private Network_lqs network_lqs_next;

    private Handler handler_atoa = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String bankname = msg.obj.toString();
            Intent intent = new Intent();
            intent.putExtra("name", et_a_add_bank_card_name.getText().toString());
            intent.putExtra("bankcardnumber", et_a_add_bank_card_number.getText().toString());
            intent.putExtra("bankname", bankname);
            intent.setClass(AddBankCardActivity.this, AddBankCardActivity1.class);
            AddBankCardActivity.this.startActivity(intent);
        }
    };

    @ViewInject(R.id.tv_first) //标题
            TextView tv_first;

    @ViewInject(R.id.iv_return) //回退
            ImageView iv_return;

    @ViewInject(R.id.tv_a_add_bank_next) //下一步
            TextView tv_a_add_bank_next;

    @ViewInject(R.id.et_a_add_bank_card_name) //et持卡人
            EditText et_a_add_bank_card_name;

    @ViewInject(R.id.et_a_add_bank_card_number) //et卡号
            EditText et_a_add_bank_card_number;

    @ViewInject(R.id.iv_a_add_bank_card_cancelname) //iv取消持卡人
            ImageView iv_a_add_bank_card_cancelname;

    @ViewInject(R.id.iv_a_add_bank_card_cancelcardnumber) //iv取消卡号
            ImageView iv_a_add_bank_card_cancelcardnumber;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (1 == resultCode) {
            switch (requestCode) {
                case Staticid.AddBankCardActivity_AddBankCardActivity1:
                    break;
            }
        }
    }

    private void init_lqs() {
        network_lqs_next = new Network_lqs() {
            @Override
            public void callback(boolean success, String result) {
                if (success) {
                    try {
                        JSONArray ja = new JSONArray(result);
                        JSONObject jo = ja.getJSONObject(0);
                        String bankname = jo.getString("bankname");
                        Message msg = new Message();
                        msg.obj = bankname;
                        handler_atoa.sendMessage(msg);
                    } catch (JSONException je) {
                        Toast.makeText(AddBankCardActivity.this, "error", Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(AddBankCardActivity.this, "error", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddBankCardActivity.this, "请输入正确的银行卡号", Toast.LENGTH_LONG).show();
                }
                tv_a_add_bank_next.setEnabled(true);
            }
        };
        et_a_add_bank_card_name.addTextChangedListener(new Etlistener_textwatcher(et_a_add_bank_card_name) {
            @Override
            public void callback_empty() {
                iv_a_add_bank_card_cancelname.setVisibility(View.GONE);
            }

            @Override
            public void callback_notempty() {
                iv_a_add_bank_card_cancelname.setVisibility(View.VISIBLE);
            }
        });
        et_a_add_bank_card_number.addTextChangedListener(new Etlistener_textwatcher(et_a_add_bank_card_number) {
            @Override
            public void callback_empty() {
                iv_a_add_bank_card_cancelcardnumber.setVisibility(View.GONE);
            }

            @Override
            public void callback_notempty() {
                iv_a_add_bank_card_cancelcardnumber.setVisibility(View.VISIBLE);
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
        tv_a_add_bank_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_return:
                onBackPressed();
                break;
            case R.id.tv_a_add_bank_next:
                if (et_a_add_bank_card_name.length() <= 0) {
                    Toast.makeText(this, "请输入持卡人姓名", Toast.LENGTH_LONG).show();
                }
                if (et_a_add_bank_card_name.length() <= 0) {
                    Toast.makeText(this, "请输入银行卡号", Toast.LENGTH_SHORT).show();
                } else {
                    tv_a_add_bank_next.setEnabled(false);
                    RequestParams requestParams = new RequestParams(Staticdata.url_getbankcardname);
                    requestParams.addBodyParameter("cardnumber", et_a_add_bank_card_number.getText().toString());
                    network_lqs_next.post_jsonobject(requestParams);
                }
                break;
            case R.id.iv_a_add_bank_card_cancelname:
                et_a_add_bank_card_name.setText("");
                break;
            case R.id.iv_a_add_bank_card_cancelcardnumber:
                et_a_add_bank_card_number.setText("");
                break;
        }

    }
}
