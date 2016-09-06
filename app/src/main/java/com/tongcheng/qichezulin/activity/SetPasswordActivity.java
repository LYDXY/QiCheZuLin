package com.tongcheng.qichezulin.activity;

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
import com.tongcheng.qichezulin.Param.ParamCheckCode;
import com.tongcheng.qichezulin.Param.ParamSetPassword;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.model.CheckCodeModel;
import com.tongcheng.qichezulin.model.JsonBase;
import com.tongcheng.qichezulin.model.UserIDModel;
import com.tongcheng.qichezulin.utils.Timekeeper_lqs;
import com.tongcheng.qichezulin.utils.UtilsJson;
import com.tongcheng.qichezulin.utils.UtilsUser;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by 林尧 on 2016/8/10.
 */

@ContentView(R.layout.activity_setpassword)
public class SetPasswordActivity extends PuTongActivity {

    private String verificationcode = "";

    private Timekeeper_lqs timekeeper_lqs;

    @ViewInject(R.id.et_a_set_password_inputnewpassword) //et新密码
            EditText et_a_set_password_inputnewpassword;

    @ViewInject(R.id.iv_a_setpassword_cancelnewpassword) //iv取消新密码
            ImageView iv_a_setpassword_cancelnewpassword;

    @ViewInject(R.id.et_a_set_password_inputverificationcode) //et验证码
            EditText et_a_set_password_inputverificationcode;

    @ViewInject(R.id.iv_a_set_password_cancelverificationcode) //iv取消验证码
            ImageView iv_a_set_password_cancelverificationcode;

    @ViewInject(R.id.tv_a_set_password_getverificationcode) //tv获取验证码
            TextView tv_a_set_password_getverificationcode;


    @Override
    void initData() {
    }

    @Override
    void initView() {
        tv_first.setVisibility(View.VISIBLE);
        tv_second.setVisibility(View.VISIBLE);
        tv_first.setText("设置密码");
        tv_second.setText("完成");
        tv_second.setOnClickListener(this);
        tv_a_set_password_getverificationcode.setOnClickListener(this);
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
                }
                if (!et_a_set_password_inputverificationcode.getText().toString().equals(verificationcode)) {
                    Toast.makeText(this, "验证码错误", Toast.LENGTH_LONG).show();
                } else {
                    set_pw(UtilsUser.getUserID(this), et_a_set_password_inputnewpassword.getText().toString(), UtilsUser.getToken(this));
                }
                break;
            case R.id.tv_a_set_password_getverificationcode:
                tv_a_set_password_getverificationcode.setEnabled(false);
                timekeeper_lqs.start(30);
                get_checkcode(UtilsUser.getUser(this).FMobilePhone);
                break;
            case R.id.iv_a_setpassword_cancelnewpassword:
                et_a_set_password_inputnewpassword.setText("");
                break;
            case R.id.iv_a_set_password_cancelverificationcode:
                et_a_set_password_inputverificationcode.setText("");
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

    //设置密码
    public void set_pw(String user_id, String newphone, String token) {
        ParamSetPassword paramSetPassword = new ParamSetPassword();
        //修改
        paramSetPassword.user_id = UtilsUser.getUserID(this);
        paramSetPassword.new_password = et_a_set_password_inputverificationcode.getText().toString().trim();
        paramSetPassword.token = UtilsUser.getToken(this);
        Callback.Cancelable cancelable
                = x.http().post(paramSetPassword, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    UtilsJson.printJsonData(result);
                    Gson gson = new Gson();
                    Type type = new TypeToken<JsonBase<ArrayList<UserIDModel>>>() {
                    }.getType();
                    JsonBase<ArrayList<UserIDModel>> base = gson
                            .fromJson(result, type);
                    JLog.w(base.data.size() + "");
                    if (base.status.trim().equals("1")) {
                        Toast.makeText(SetPasswordActivity.this, "设置密码成功", Toast.LENGTH_LONG).show();
                        SetPasswordActivity.this.finish();
                    } else {
                        Toast.makeText(SetPasswordActivity.this, "设置密码失败", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception E) {
                    Toast.makeText(SetPasswordActivity.this, "设置密码失败", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(SetPasswordActivity.this, "设置密码失败", Toast.LENGTH_LONG).show();
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
        Toast.makeText(SetPasswordActivity.this, "获取验证码失败", Toast.LENGTH_LONG).show();
        timekeeper_lqs.stop();
        tv_a_set_password_getverificationcode.setText("获取验证码");
        tv_a_set_password_getverificationcode.setEnabled(true);
    }

    private void init_lqs() {
        timekeeper_lqs = new Timekeeper_lqs() {
            @Override
            public void callback(int timekey) {
                tv_a_set_password_getverificationcode.setText(timekey + "秒后重新发送");
                if (timekey <= 0) {
                    tv_a_set_password_getverificationcode.setText("获取验证码");
                    tv_a_set_password_getverificationcode.setEnabled(true);
                }
            }
        };

        et_a_set_password_inputnewpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_a_set_password_inputnewpassword.length() > 0) {
                    iv_a_setpassword_cancelnewpassword.setVisibility(View.VISIBLE);
                } else {
                    iv_a_setpassword_cancelnewpassword.setVisibility(View.GONE);
                }
            }
        });
        et_a_set_password_inputverificationcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_a_set_password_inputverificationcode.length() > 0) {
                    iv_a_set_password_cancelverificationcode.setVisibility(View.VISIBLE);
                } else {
                    iv_a_set_password_cancelverificationcode.setVisibility(View.GONE);
                }
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
