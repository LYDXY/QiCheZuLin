package com.tongcheng.qichezulin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.tongcheng.qichezulin.Param.ParamAddBankcardname;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.model.BandcardnameModel;
import com.tongcheng.qichezulin.model.JsonBase;
import com.tongcheng.qichezulin.utils.UtilsJson;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * Created by 林尧 on 2016/7/22.
 */


@ContentView(R.layout.activity_add_bank_card)
public class AddBankCardActivity extends Activity implements View.OnClickListener {

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

    private Handler handler_atoa = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent();
            intent.putExtra("name", et_a_add_bank_card_name.getText().toString());
            intent.putExtra("cardnum", et_a_add_bank_card_number.getText().toString());
            intent.putExtra("bankname", (String) msg.obj);
            intent.setClass(AddBankCardActivity.this, AddBankCardActivity1.class);
            AddBankCardActivity.this.startActivity(intent);
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (1 == resultCode) {
        }
    }

    private void init_lqs() {

        et_a_add_bank_card_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_a_add_bank_card_name.length() > 0) {
                    iv_a_add_bank_card_cancelname.setVisibility(View.VISIBLE);
                } else {
                    iv_a_add_bank_card_cancelname.setVisibility(View.GONE);
                }
            }
        });
        et_a_add_bank_card_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_a_add_bank_card_number.length() > 0) {
                    iv_a_add_bank_card_cancelcardnumber.setVisibility(View.VISIBLE);
                } else {
                    iv_a_add_bank_card_cancelcardnumber.setVisibility(View.GONE);
                }
            }
        });
    }

    //获取银行卡名
    public void get_bankcardname(String cardnumber) {
        ParamAddBankcardname paramAddBankcardname = new ParamAddBankcardname();
        paramAddBankcardname.cardnumber = cardnumber;
        Callback.Cancelable cancelable
                = x.http().post(paramAddBankcardname, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    UtilsJson.printJsonData(result);
                    Gson gson = new Gson();
                    Type type = new TypeToken<JsonBase<ArrayList<BandcardnameModel>>>() {
                    }.getType();
                    JsonBase<ArrayList<BandcardnameModel>> base = gson
                            .fromJson(result, type);
                    JLog.w(base.data.size() + "");
                    if (base.data != null) {
                        if (base.data.size() > 0) {
                            JLog.w("获取银行名成功" + base.data);
                            String bankname = base.data.get(0).bankname;
                            JLog.w("获取银行名成功" + bankname);
                            Message msg = new Message();
                            msg.obj = bankname;
                            handler_atoa.sendMessage(msg);
                        } else {
                            Toast.makeText(AddBankCardActivity.this, "error", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        JLog.w("获取银行名失败");
                        Toast.makeText(AddBankCardActivity.this, "error", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception E) {
                    Toast.makeText(AddBankCardActivity.this, "Exception", Toast.LENGTH_LONG).show();
                }
                tv_a_add_bank_next.setEnabled(true);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(AddBankCardActivity.this, "请输入正确的银行卡号", Toast.LENGTH_LONG).show();
                tv_a_add_bank_next.setEnabled(true);
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
                    get_bankcardname(et_a_add_bank_card_number.getText().toString());
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
