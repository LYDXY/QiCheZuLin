package com.tongcheng.qichezulin.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.Param.ParamCheckCode;
import com.tongcheng.qichezulin.Param.ParamRegist;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.model.CheckCodeModel;
import com.tongcheng.qichezulin.model.JsonBase;
import com.tongcheng.qichezulin.model.RegistModel;
import com.tongcheng.qichezulin.utils.Utils;
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

@ContentView(R.layout.activity_regist)
public class RegistActivity extends PuTongFragmentActivity {


    @ViewInject(R.id.tv_get_check_code)
    TextView tv_get_check_code;

    @ViewInject(R.id.et_phone_number)
    EditText et_phone_number;

    @ViewInject(R.id.et_check_code)
    EditText et_check_code;

    @ViewInject(R.id.btn_regist)
    Button btn_regist;

    @ViewInject(R.id.iv_qu_xiao)
    ImageView iv_qu_xiao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JLog.v("onCreate");
        initData();
        initView();
        setListenerOnView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                onBackPressed();
                break;
            case R.id.tv_get_check_code:
                JLog.i("获取验证码");
                boolean flag = Utils.isPhoneNumber(et_phone_number, this);
                if (flag) {
                    do_get_checkcode();
                }
                break;
            case R.id.btn_regist:
                JLog.i("点击注册");
                boolean flag0 = Utils.isPhoneNumber(et_phone_number, this);
                if (flag0) {
                    boolean flag1 = Utils.yanZhengMaIsEmpty(et_check_code, this, "验证码不能为空");
                    if (flag1) {
                        do_regitst();
                    } else {

                    }
                } else {

                }
                break;
            case R.id.iv_qu_xiao:
                JLog.i("清空手机号码");
                et_phone_number.setText("");
                break;

        }
    }

    @Override
    void initData() {

    }

    @Override
    void initView() {
        tv_first.setVisibility(View.VISIBLE);
        tv_first.setText("注册");
    }

    @Override
    void setListenerOnView() {
        super.setListenerOnView();
        tv_get_check_code.setOnClickListener(this);
        btn_regist.setOnClickListener(this);
        iv_qu_xiao.setOnClickListener(this);
    }

    //注册获取验证码
    private void do_get_checkcode() {
        ParamCheckCode paramCheckCode = new ParamCheckCode();
        paramCheckCode.phone = et_phone_number.getText().toString().trim();
        Callback.Cancelable cancelable
                = x.http().post(paramCheckCode, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {

                UtilsJson.printJsonData(result);
                Gson gson = new Gson();
                Type type = new TypeToken<JsonBase<ArrayList<CheckCodeModel>>>() {
                }.getType();
                JsonBase<ArrayList<CheckCodeModel>> base = gson
                        .fromJson(result, type);
                JLog.i(base.data.size() + "");
                if (base.data != null) {
                    if (base.data.size() > 0) {
                        JLog.i("获取验证码成功");
                        et_check_code.setText(base.data.get(0).code);
                    }
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                JLog.i(isOnCallback + "");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                JLog.i(cex + "");
            }

            @Override
            public void onFinished() {

            }
        });

    }


    // 注册操作
    private void do_regitst() {
        ParamRegist paramRegist = new ParamRegist();
        paramRegist.phone = et_phone_number.getText().toString().trim();
        Callback.Cancelable cancelable
                = x.http().post(paramRegist, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                UtilsJson.printJsonData(result);
                Gson gson = new Gson();
                Type type = new TypeToken<JsonBase<ArrayList<RegistModel>>>() {
                }.getType();
                JsonBase<ArrayList<RegistModel>> base = gson
                        .fromJson(result, type);
                if (!base.status.toString().trim().equals("0")) {
                    if (base.data != null) {
                        JLog.i(base.data.size() + "");
                        if (base.data.size() > 0) {
                            JLog.i("注册成功");
                            Utils.ShowText(RegistActivity.this, base.info.toString());
                            UtilsUser.setSP(getApplication(), UtilsUser.KEY_USER_ID, base.data.get(0).user_id); //缓存注册的用户id
                        }
                    }
                } else {
                    Utils.ShowText(RegistActivity.this, base.info.toString());
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                JLog.i(isOnCallback + "");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                JLog.i(cex + "");
            }

            @Override
            public void onFinished() {

            }
        });
    }

}
