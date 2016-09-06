package com.tongcheng.qichezulin.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiongbull.jlog.JLog;
import com.tongcheng.lqs.listener.Etlistener_textwatcher;
import com.tongcheng.lqs.network.Network_lqs;
import com.tongcheng.lqs.staticdata.Staticdata;
import com.tongcheng.lqs.utils.Log_lqs;
import com.tongcheng.lqs.utils.Timekeeper_lqs;
import com.tongcheng.qichezulin.Param.ParamCheckCode;
import com.tongcheng.qichezulin.Param.ParamUpdateUser;
import com.tongcheng.qichezulin.Param.ParamUserJf;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.model.CheckCodeModel;
import com.tongcheng.qichezulin.model.JiFenModel;
import com.tongcheng.qichezulin.model.JsonBase;
import com.tongcheng.qichezulin.model.JsonBase2;
import com.tongcheng.qichezulin.model.UserModel;
import com.tongcheng.qichezulin.utils.UtilsJson;
import com.tongcheng.qichezulin.utils.UtilsUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 林尧 on 2016/8/10.
 */

@ContentView(R.layout.activity_bindphonenumber)
public class BindPhoneNumberActivity extends PuTongActivity {

    private String verificationcode = "";

    private Timekeeper_lqs timekeeper_lqs;


    @ViewInject(R.id.et_a_bindphonenumber_inputphonenumber) //et手机号码
            EditText et_a_bindphonenumber_inputphonenumber;

    @ViewInject(R.id.et_a_bindphonenumber_inputverificationcode) //et验证码
            EditText et_a_bindphonenumber_inputverificationcode;

    @ViewInject(R.id.tv_a_bindphonenumber_getverificationcode) //tv获取验证码
            TextView tv_a_bindphonenumber_getverificationcode;

    @ViewInject(R.id.iv_a_bindphonenumber_cancelphonenumber) //iv清空et手机号码
            ImageView iv_a_bindphonenumber_cancelphonenumber;

    @ViewInject(R.id.iv_a_bindphonenumber_cancelverificationcode) //iv清空et验证码
            ImageView iv_a_bindphonenumber_cancelverificationcode;

    @Override
    void initData() {

    }

    @Override
    void initView() {
        tv_first.setVisibility(View.VISIBLE);
        tv_second.setVisibility(View.VISIBLE);
        tv_first.setText("绑定手机号");
        tv_second.setText("完成");
        tv_a_bindphonenumber_getverificationcode.setOnClickListener(this);
        iv_a_bindphonenumber_cancelphonenumber.setOnClickListener(this);
        iv_a_bindphonenumber_cancelverificationcode.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_return:
                onBackPressed();
                break;
            case R.id.tv_second:
                if (verificationcode.equals("")) {
                    Toast.makeText(this, "请先获取验证码", Toast.LENGTH_LONG).show();
                } else if (!et_a_bindphonenumber_inputverificationcode.getText().toString().equals(verificationcode)) {
                    Toast.makeText(this, "验证码错误", Toast.LENGTH_LONG).show();
                } else {
                    bind_pn("1", et_a_bindphonenumber_inputphonenumber.getText().toString());
                    this.finish();
                }
                break;

            case R.id.tv_a_bindphonenumber_getverificationcode:
                tv_a_bindphonenumber_getverificationcode.setEnabled(false);
                timekeeper_lqs.start(30);
                if (et_a_bindphonenumber_inputphonenumber.length() == 11)
                    get_checkcode(et_a_bindphonenumber_inputphonenumber.getText().toString().trim());
                else
                    Toast.makeText(BindPhoneNumberActivity.this, "请输入正确的手机号", Toast.LENGTH_LONG).show();
                break;
            case R.id.iv_a_bindphonenumber_cancelphonenumber:
                et_a_bindphonenumber_inputphonenumber.setText("");
                break;
            case R.id.iv_a_bindphonenumber_cancelverificationcode:
                et_a_bindphonenumber_inputverificationcode.setText("");
                break;

        }

    }

    //获取验证码
    public void get_checkcode(String phone) {
        ParamCheckCode paramCheckCode = new ParamCheckCode();
        //修改
        paramCheckCode.phone = phone;
        Callback.Cancelable cancelable
                = x.http().post(paramCheckCode, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    UtilsJson.printJsonData(result);
                    Gson gson = new Gson();
                    Type type = new TypeToken<JsonBase<ArrayList<CheckCodeModel>>>() {
                    }.getType();
                    JsonBase<ArrayList<CheckCodeModel>> base = gson
                            .fromJson(result, type);
                    JLog.w(base.data.size() + "");
                    if (base.data != null) {
                        if (base.data.size() > 0) {
                            JLog.w("获取验证码成功");
                            verificationcode = base.data.get(0).code;
                        } else {
                            error_getcheckcode();
                        }
                    } else {
                        JLog.w("获取验证码失败");
                        error_getcheckcode();
                    }
                } catch (Exception E) {
                    error_getcheckcode();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                error_getcheckcode();
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }


            @Override
            public void onFinished() {
            }
        });
    }


    private void error_getcheckcode() {
        Toast.makeText(BindPhoneNumberActivity.this, "获取验证码失败", Toast.LENGTH_LONG).show();
        timekeeper_lqs.stop();
        tv_a_bindphonenumber_getverificationcode.setText("获取验证码");
        tv_a_bindphonenumber_getverificationcode.setEnabled(true);
    }

    //绑定手机号
    public void bind_pn(String user_id, String phone) {
        ParamUpdateUser paramUpdateUser = new ParamUpdateUser();
        //修改
        paramUpdateUser.user_id = user_id;
        paramUpdateUser.phone = phone;
        Callback.Cancelable cancelable
                = x.http().post(paramUpdateUser, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    UtilsJson.printJsonData(result);
                    Gson gson = new Gson();
                    Type type = new TypeToken<JsonBase<ArrayList<UserModel>>>() {
                    }.getType();
                    JsonBase<ArrayList<UserModel>> base = gson
                            .fromJson(result, type);
                    JLog.w(base.data.size() + "");
                    if (Integer.valueOf(base.status.trim()) == 1) {
                        Toast.makeText(BindPhoneNumberActivity.this, "绑定手机号成功", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(BindPhoneNumberActivity.this, "绑定手机号失败", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception E) {
                    Toast.makeText(BindPhoneNumberActivity.this, "绑定手机号失败", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(BindPhoneNumberActivity.this, "绑定手机号失败", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }


            @Override
            public void onFinished() {
            }
        });
    }

    private void init_lqs() {
        timekeeper_lqs = new Timekeeper_lqs() {
            @Override
            public void callback(int timekey) {
                Log_lqs.activity_log("Timekey", "" + timekey);
                tv_a_bindphonenumber_getverificationcode.setText(timekey + "秒后重新发送");
                if (timekey <= 0) {
                    tv_a_bindphonenumber_getverificationcode.setText("获取验证码");
                    tv_a_bindphonenumber_getverificationcode.setEnabled(true);
                }
            }
        };
        et_a_bindphonenumber_inputphonenumber.addTextChangedListener(new Etlistener_textwatcher(et_a_bindphonenumber_inputphonenumber) {
            @Override
            public void callback_empty() {
                iv_a_bindphonenumber_cancelphonenumber.setVisibility(View.INVISIBLE);
            }

            @Override
            public void callback_notempty() {
                iv_a_bindphonenumber_cancelphonenumber.setVisibility(View.VISIBLE);
            }
        });
        et_a_bindphonenumber_inputverificationcode.addTextChangedListener(new Etlistener_textwatcher(et_a_bindphonenumber_inputverificationcode) {
            @Override
            public void callback_empty() {
                iv_a_bindphonenumber_cancelverificationcode.setVisibility(View.INVISIBLE);
            }

            @Override
            public void callback_notempty() {
                iv_a_bindphonenumber_cancelverificationcode.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListenerOnView();
        init_lqs();
    }

    @Override
    protected void onResume() {
        super.onResume();
        JLog.w("onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JLog.w("onDestroy");
    }
}
