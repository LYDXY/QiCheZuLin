package com.tongcheng.qichezulin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.Param.ParamUpdateUser;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.model.JsonBase;
import com.tongcheng.qichezulin.model.UserModel;
import com.tongcheng.qichezulin.utils.UtilsJson;
import com.tongcheng.qichezulin.utils.UtilsUser;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by 林尧 on 2016/8/3.
 */
@ContentView(R.layout.activity_update_username)
public class UpdateAccountUserNameActivity extends PuTongActivity {


    private String name = "";
    private int sex = -1;

    @ViewInject(R.id.et_a_update_username_name) //et昵称
            EditText et_a_update_username_name;

    @ViewInject(R.id.iv_a_bindphonenumber_cancelname) //iv清空et昵称
            ImageView iv_a_bindphonenumber_cancelname;

    @ViewInject(R.id.rb_a_update_username_man) //rb男
            RadioButton rb_a_update_username_man;

    @ViewInject(R.id.rb_a_update_username_women) //rb女
            RadioButton rb_a_update_username_women;

    //修改个人信息
    public void update_name(String user_id, final String name, final int sex) {
        ParamUpdateUser paramUpdateUser = new ParamUpdateUser();
        paramUpdateUser.user_id = user_id;
        paramUpdateUser.name = name;
        paramUpdateUser.sex = String.valueOf(sex).trim();
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
                        Toast.makeText(UpdateAccountUserNameActivity.this, "修改个人信息成功", Toast.LENGTH_LONG).show();
                        UtilsUser.setSP(UpdateAccountUserNameActivity.this, UtilsUser.USER_NAME, name);
                        UtilsUser.setSP(UpdateAccountUserNameActivity.this, UtilsUser.USER_SEX, sex);
                        Intent intent = new Intent();
                        intent.putExtra("name", et_a_update_username_name.getText().toString());
                        UpdateAccountUserNameActivity.this.setResult(1, intent);
                        UpdateAccountUserNameActivity.this.finish();
                    } else {
                        Toast.makeText(UpdateAccountUserNameActivity.this, "修改个人信息失败", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception E) {
                    Toast.makeText(UpdateAccountUserNameActivity.this, "修改个人信息失败", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(UpdateAccountUserNameActivity.this, "修改个人信息失败", Toast.LENGTH_LONG).show();
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
        et_a_update_username_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_a_update_username_name.length() > 0) {
                    iv_a_bindphonenumber_cancelname.setVisibility(View.VISIBLE);
                } else {
                    iv_a_bindphonenumber_cancelname.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListenerOnView();
        initData();
        initView();
        init_lqs();
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
        et_a_update_username_name.setOnClickListener(this);
        iv_a_bindphonenumber_cancelname.setOnClickListener(this);
        rb_a_update_username_man.setOnClickListener(this);
        rb_a_update_username_women.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                this.finish();
                break;
            case R.id.tv_second:
                if (et_a_update_username_name.length() > 0 && sex != -1) {
                    update_name(UtilsUser.getUserID(this), et_a_update_username_name.getText().toString(), sex);
                } else {
                    Toast.makeText(UpdateAccountUserNameActivity.this, "请先填写好个人信息", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.iv_a_bindphonenumber_cancelname:
                et_a_update_username_name.setText("");
                break;
            case R.id.rb_a_update_username_man:
                sex = 1;
                break;
            case R.id.rb_a_update_username_women:
                sex = 0;
                break;
        }
    }

}
