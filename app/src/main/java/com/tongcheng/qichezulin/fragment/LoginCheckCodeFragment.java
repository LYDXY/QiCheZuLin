package com.tongcheng.qichezulin.fragment;

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
import com.tongcheng.qichezulin.Param.ParamLoginByCheckCode;
import com.tongcheng.qichezulin.Param.ParamLoginPassword;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.activity.MainActivity2;
import com.tongcheng.qichezulin.model.CheckCodeModel;
import com.tongcheng.qichezulin.model.JsonBase;
import com.tongcheng.qichezulin.model.RegistModel;
import com.tongcheng.qichezulin.utils.Utils;
import com.tongcheng.qichezulin.utils.UtilsJson;
import com.tongcheng.qichezulin.utils.UtilsTiaoZhuang;
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

@ContentView(R.layout.fragment_login_checkcode)
public class LoginCheckCodeFragment extends PuTongFragment {


    @ViewInject(R.id.et_phone_number)
    EditText et_phone_number;
    @ViewInject(R.id.et_password)
    EditText et_password;
    @ViewInject(R.id.iv_qu_xiao)
    ImageView iv_qu_xiao;
    @ViewInject(R.id.btn_login)
    Button btn_login;
    @ViewInject(R.id.tv_get_yan_zheng_ma)
    TextView tv_get_yan_zheng_ma;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setClickListenerOnView();
    }

    @Override
    void setClickListenerOnView() {
        btn_login.setOnClickListener(this);
        iv_qu_xiao.setOnClickListener(this);
        tv_get_yan_zheng_ma.setOnClickListener(this);
    }

    @Override
    void initData() {

    }

    @Override
    void initView() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_get_yan_zheng_ma:
                JLog.w("获取验证码");
                boolean flag = Utils.isPhoneNumber(et_phone_number, getActivity());
                if (flag) {
                    do_get_checkcode();
                }
                break;
            case R.id.iv_qu_xiao:
                JLog.w("清空");
                et_phone_number.setText("");
                break;
            case R.id.btn_login:
                JLog.w("登录");
                boolean flag0 = Utils.isPhoneNumber(et_phone_number, getActivity());
                if (flag0) {
                    boolean flag1 = Utils.yanZhengMaIsEmpty(et_password, getActivity(), "验证码不能为空");
                    if (flag1) {
                        //登录操作
                        do_login_by_check_code();
                    } else {

                    }
                } else {

                }
                break;
        }
    }

    //登录获取验证码
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
                JLog.w(base.data.size() + "");
                if (base.data != null) {
                    if (base.data.size() > 0) {
                        JLog.w("获取验证码成功");
                        et_password.setText(base.data.get(0).code);
                    }
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                JLog.w(isOnCallback + "");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                JLog.w(cex + "");
            }

            @Override
            public void onFinished() {

            }
        });

    }


    //通过验证码进行登录操作
    private void do_login_by_check_code() {
        ParamLoginByCheckCode paramRegist = new ParamLoginByCheckCode();
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
                        JLog.w(base.data.size() + "");
                        if (base.data.size() > 0) {
                            JLog.w("登陆成功");
                            Utils.ShowText(getActivity(), base.info.toString());
                            UtilsUser.setSP(getContext(), UtilsUser.KEY_USER_ID, base.data.get(0).user_id); //缓存注册的用户id
                            UtilsTiaoZhuang.ToAnotherActivity(getActivity(), MainActivity2.class);
                        }
                    }
                } else {
                    Utils.ShowText(getActivity(), base.info.toString());
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                JLog.w(isOnCallback + "");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                JLog.w(cex + "");
            }

            @Override
            public void onFinished() {

            }
        });
    }

}