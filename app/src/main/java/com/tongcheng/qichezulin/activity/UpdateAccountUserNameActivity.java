package com.tongcheng.qichezulin.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.Param.ParamUpdateUser;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.model.JsonBase;
import com.tongcheng.qichezulin.model.JsonBase2;
import com.tongcheng.qichezulin.model.UserIDModel;
import com.tongcheng.qichezulin.model.UserModel;
import com.tongcheng.qichezulin.utils.Utils;
import com.tongcheng.qichezulin.utils.UtilsJson;
import com.tongcheng.qichezulin.utils.UtilsString;
import com.tongcheng.qichezulin.utils.UtilsTiaoZhuang;
import com.tongcheng.qichezulin.utils.UtilsUser;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by 林尧 on 2016/8/3.
 */
@ContentView(R.layout.activity_update_username)
public class UpdateAccountUserNameActivity extends PuTongActivity {


    public static String new_user_name="";

    @ViewInject(R.id.et_user_name)
    EditText et_user_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListenerOnView();
        initData();
        initView();
    }

    @Override
    void initData() {

    }

    @Override
    void initView() {
        tv_first.setText("修改昵称");
        tv_first.setVisibility(View.VISIBLE);
        tv_second.setText("保存");
        tv_second.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                onBackPressed();
                break;
            case R.id.tv_second:
                if (!et_user_name.getText().equals("")) {
                    if (!UtilsUser.getUser(this).PID.equals("")&&UtilsUser.getUser(this).PID!=null) {
                        update_user(et_user_name.getText().toString(),UtilsUser.getUser(this).PID);
                    }
                }else {
                    Utils.ShowText2(UpdateAccountUserNameActivity.this,"请输入昵称,再点击保存");
                }

                break;
        }
    }

    //跟新用户的信息
    public void update_user(String username,String user_id){
        JLog.w(username+""+user_id);
        ParamUpdateUser paramUpdateUser=new ParamUpdateUser();
        paramUpdateUser.name=username;
        paramUpdateUser.user_id=user_id;
        paramUpdateUser.phone="";
        paramUpdateUser.sex="";
        Callback.Cancelable cancelable
                = x.http().post(paramUpdateUser, new Callback.CommonCallback<String>() {
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }


            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String result) {
                try {
                    UtilsJson.printJsonData(result);
                    Gson gson = new Gson();
                    Type type = new TypeToken<JsonBase<List<UserIDModel>>>() {
                    }.getType();
                    JsonBase<List<UserIDModel>> base = gson
                            .fromJson(result, type);
                    if (!base.status.toString().trim().equals("0")) {
                        if (base.data != null) {
                            JLog.w("修改用户名成功");
                            Utils.ShowText(UpdateAccountUserNameActivity.this,"修改昵称成功");
                            UserModel userModel=UtilsUser.getUser(getApplicationContext());
                            userModel.FUserName=et_user_name.getText().toString();
                            UtilsUser.saveUser(getApplicationContext(),userModel);
                            new_user_name=et_user_name.getText().toString();
                        }

                    } else {
                        JLog.w("修改昵称失败");
                    }
                } catch (Exception E) {
                    E.printStackTrace();
                }

            }
        });
    }
}
