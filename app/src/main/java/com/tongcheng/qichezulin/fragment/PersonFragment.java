package com.tongcheng.qichezulin.fragment;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.code19.library.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.Param.ParamGetUserInfo;
import com.tongcheng.qichezulin.Param.ParamRegist;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.activity.AccountActivity;
import com.tongcheng.qichezulin.config.RootApp;
import com.tongcheng.qichezulin.model.JsonBase;
import com.tongcheng.qichezulin.model.RegistModel;
import com.tongcheng.qichezulin.model.UserModel;
import com.tongcheng.qichezulin.utils.Utils;
import com.tongcheng.qichezulin.utils.UtilsDate;
import com.tongcheng.qichezulin.utils.UtilsFastBlur;
import com.tongcheng.qichezulin.utils.UtilsImage;
import com.tongcheng.qichezulin.utils.UtilsJson;
import com.tongcheng.qichezulin.utils.UtilsString;
import com.tongcheng.qichezulin.utils.UtilsTiaoZhuang;
import com.tongcheng.qichezulin.utils.UtilsUser;
import com.wingjay.blurimageviewlib.BlurImageView;
import com.wingjay.blurimageviewlib.FastBlurUtil;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.blurry.Blurry;

/**
 * 个人片段
 */

@ContentView(R.layout.fragment_person)
public class PersonFragment extends PuTongFragment {


    @ViewInject(R.id.iv_blur)
    ImageView iv_blur;
    @ViewInject(R.id.iv_head_photo)
    CircleImageView iv_head_photo;
    @ViewInject(R.id.rrl_zhang_hao)
    PercentRelativeLayout rrl_zhang_hao;
    //用户账号
    @ViewInject(R.id.tv_user_account)
    TextView tv_user_account;
    //从缓存中获取 user_id
    private String user_id;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
        setClickListenerOnView();
        //获取个人信息
        get_User_info();

    }

    @Override
    void setClickListenerOnView() {
        rrl_zhang_hao.setOnClickListener(this);
    }

    @Override
    void initData() {
        user_id = (String) UtilsUser.getSp(getContext(), UtilsUser.KEY_USER_ID, "");
        JLog.w("参数user_id:" + user_id);
    }

    @Override
    void initView() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rrl_zhang_hao:
                UtilsTiaoZhuang.ToAnotherActivity(getActivity(), AccountActivity.class);
                break;
        }
    }

    //获取个人信息
    public void get_User_info() {
        ParamGetUserInfo paramGetUserInfo = new ParamGetUserInfo();
        paramGetUserInfo.user_id = user_id.trim();
        Callback.Cancelable cancelable
                = x.http().post(paramGetUserInfo, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                UtilsJson.printJsonData(result);
                Gson gson = new Gson();
                Type type = new TypeToken<JsonBase<UserModel>>() {
                }.getType();
                JsonBase<UserModel> base = gson
                        .fromJson(result, type);
                if (!base.status.toString().trim().equals("0")) {
                    if (base.data != null) {
                        JLog.w("获取个人信息成功");
                        //判断有没有图片
                        if (StringUtils.isEmpty(base.data.FImg)) {
                            iv_head_photo.setImageResource(R.mipmap.default_head_photo);
                          /*  x.image().bind(iv_head_photo, UUU);
                            Blurry.with(getActivity())
                                    .radius(15)
                                    .sampling(2)
                                    .color(Color.argb(66, 0, 255, 255))
                                    .capture(iv_blur)
                                    .into(iv_blur);*/

                        } else {
                            x.image().bind(iv_head_photo,
                                    base.data.FImg);
                            x.image().bind(iv_blur,
                                    base.data.FImg);

                        }

                        tv_user_account.setText(UtilsString.hidePhoneNumber(base.data.FUserName));
                        UtilsUser.saveUser(getContext(), base.data);
                    }
                } else {
                    JLog.w("获取个人信息失败");
                }
            }


            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

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
    public void onStop() {
        super.onStop();
        JLog.w("onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        JLog.w("onDestroy");
    }

    @Override
    public void onPause() {
        super.onPause();
        JLog.w("onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        JLog.w("onResume");
    }
}
