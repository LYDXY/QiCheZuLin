package com.tongcheng.qichezulin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.Param.ParamAddBankcard;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.model.BandcardModel;
import com.tongcheng.qichezulin.model.JsonBase;
import com.tongcheng.qichezulin.utils.UtilsJson;
import com.tongcheng.qichezulin.utils.UtilsUser;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * Created by 林尧 on 2016/7/22.
 */


@ContentView(R.layout.activity_add_bank_card1)
public class AddBankCardActivity1 extends Activity implements View.OnClickListener {

    private String name = "";
    private String cardnum = "";
    private String bankname = "";

    @ViewInject(R.id.tv_first) //标题
            TextView tv_first;

    @ViewInject(R.id.iv_return) //回退
            ImageView iv_return;

    @ViewInject(R.id.tv_a_add_bank_card1_name) //tv持卡人
            TextView tv_a_add_bank_card1_name;

    @ViewInject(R.id.tv_a_add_bank_card1_bankname) //tv银行卡
            TextView tv_a_add_bank_card1_bankname;

    @ViewInject(R.id.et_a_add_bank_card1_phonenumber) //et手机号
            EditText et_a_add_bank_card1_phonenumber;

    @ViewInject(R.id.iv_a_add_bank_card1_cancelphonenumber) //iv取消手机号
            ImageView iv_a_add_bank_card1_cancelphonenumber;

    @ViewInject(R.id.tv_a_add_bank_card1_ok) //iv确定
            TextView tv_a_add_bank_card1_ok;

    private void init_lqs() {
        et_a_add_bank_card1_phonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_a_add_bank_card1_phonenumber.length() > 0) {
                    iv_a_add_bank_card1_cancelphonenumber.setVisibility(View.VISIBLE);
                } else {
                    iv_a_add_bank_card1_cancelphonenumber.setVisibility(View.GONE);
                }
            }
        });
    }

    //添加银行卡
    public void add_bankcard(String user_id, String name, String mobile, String bankname, String cardnum) {
        ParamAddBankcard paramAddBankcard = new ParamAddBankcard();
        paramAddBankcard.user_id = user_id;
        paramAddBankcard.name = name;
        paramAddBankcard.mobile = mobile;
        paramAddBankcard.bankname = bankname;
        paramAddBankcard.cardnum = cardnum;
        Callback.Cancelable cancelable
                = x.http().post(paramAddBankcard, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    UtilsJson.printJsonData(result);
                    Gson gson = new Gson();
                    Type type = new TypeToken<JsonBase<ArrayList<BandcardModel>>>() {
                    }.getType();
                    JsonBase<ArrayList<BandcardModel>> base = gson
                            .fromJson(result, type);
                    JLog.w(base.data.size() + "");
                    if (base.data != null) {
                        if (base.data.size() > 0) {
                            JLog.w("添加银行卡成功" + base.data);
                            String bank_id = base.data.get(0).bank_id;
                            JLog.w("银行卡id" + bank_id);
                            Toast.makeText(AddBankCardActivity1.this, "添加银行卡成功", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(AddBankCardActivity1.this, "添加银行卡失败", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        JLog.w("添加银行卡失败");
                        Toast.makeText(AddBankCardActivity1.this, "添加银行卡名失败", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception E) {
                    Toast.makeText(AddBankCardActivity1.this, "添加银行卡失败", Toast.LENGTH_LONG).show();
                }
                tv_a_add_bank_card1_ok.setEnabled(true);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(AddBankCardActivity1.this, "error", Toast.LENGTH_LONG).show();
                tv_a_add_bank_card1_ok.setEnabled(true);
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }


            @Override
            public void onFinished() {
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
        iv_a_add_bank_card1_cancelphonenumber.setOnClickListener(this);
        iv_return.setOnClickListener(this);
        tv_a_add_bank_card1_ok.setOnClickListener(this);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        bankname = intent.getStringExtra("bankname");
        cardnum = intent.getStringExtra("cardnum");
        tv_a_add_bank_card1_name.setText(name);
        tv_a_add_bank_card1_bankname.setText(bankname);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_return:
                onBackPressed();
                break;
            case R.id.tv_a_add_bank_card1_ok:
                if (et_a_add_bank_card1_phonenumber.length() == 11) {
                    tv_a_add_bank_card1_ok.setEnabled(false);
                    add_bankcard(UtilsUser.getUserID(this), name, et_a_add_bank_card1_phonenumber.getText().toString(), bankname, cardnum);
                } else {
                    Toast.makeText(AddBankCardActivity1.this, "请输入正确的手机号", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }
}
