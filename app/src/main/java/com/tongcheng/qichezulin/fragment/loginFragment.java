package com.tongcheng.qichezulin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.Param.ParamLoginPassword;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.activity.MainActivity2;
import com.tongcheng.qichezulin.activity.RegistActivity;
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
 * 登录片段
 */

@ContentView(R.layout.fragment_login)
public class loginFragment extends PuTongFragment {


    @ViewInject(R.id.et_phone_number)
    EditText et_phone_number;
    @ViewInject(R.id.et_password)
    EditText et_password;
    @ViewInject(R.id.iv_qu_xiao)
    ImageView iv_qu_xiao;
    @ViewInject(R.id.btn_login)
    Button btn_login;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setClickListenerOnView();
    }

    @Override
    void setClickListenerOnView() {
        btn_login.setOnClickListener(this);
        iv_qu_xiao.setOnClickListener(this);
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
            case R.id.iv_qu_xiao:
                JLog.i("清空");
                et_phone_number.setText("");
                break;
            case R.id.btn_login:
                JLog.i("登录");
                boolean flag0 = Utils.isPhoneNumber(et_phone_number, getActivity());
                if (flag0) {
                    boolean flag1 = Utils.yanZhengMaIsEmpty(et_password, getActivity(), "密码不能为空");
                    if (flag1) {
                        do_login();
                    } else {

                    }
                } else {

                }
                break;
        }
    }

    //登录操作
    private void do_login() {
        ParamLoginPassword paramRegist = new ParamLoginPassword();
        paramRegist.phone = et_phone_number.getText().toString().trim();
        paramRegist.pwd = et_password.getText().toString().trim();
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
                            JLog.i("登录成功");
                            Utils.ShowText(getActivity(), base.info.toString());
                            UtilsUser.setSP(getContext(), UtilsUser.KEY_USER_ID, base.data.get(0).user_id); //缓存注册的用户id
                            UtilsTiaoZhuang.ToAnotherActivity(getActivity(), MainActivity2.class);
                            getActivity().finish();
                        }
                    }
                } else {
                    Utils.ShowText(getActivity(), base.info.toString());
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
