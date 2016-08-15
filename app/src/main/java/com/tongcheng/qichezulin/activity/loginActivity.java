package com.tongcheng.qichezulin.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.Adapter.FragmentStateAdaper;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.config.RootApp;
import com.tongcheng.qichezulin.fragment.LoginCheckCodeFragment;
import com.tongcheng.qichezulin.fragment.loginFragment;
import com.tongcheng.qichezulin.utils.UtilsTiaoZhuang;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 林尧 on 2016/7/22.
 */

@ContentView(R.layout.activity_login_regist)
public class loginActivity extends PuTongFragmentActivity {


    //切换的 tab
    @ViewInject(R.id.tab)
    TabLayout tab;
    // viewpager
    @ViewInject(R.id.v4_vp)
    ViewPager v4_vp;
    //标签
    List<String> stringList;
    //片段集合
    List<Fragment> fragmentList;

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
            case R.id.tv_first:
                JLog.d("登录");
                break;
            case R.id.tv_second:
                JLog.d("注册");
                UtilsTiaoZhuang.ToAnotherActivity(this, RegistActivity.class);
                break;
        }
    }

    @Override
    void initData() {
        stringList = new ArrayList<>();
        stringList.add("密码登录");
        stringList.add("验证码登录");
        fragmentList = new ArrayList<>();
        fragmentList.add(new loginFragment());
        fragmentList.add(new LoginCheckCodeFragment());
    }

    @Override
    void initView() {
        tv_first.setVisibility(View.VISIBLE);
        tv_second.setVisibility(View.VISIBLE);
        tv_first.setText("登录");
        tv_second.setText("注册");
        v4_vp.setAdapter(new FragmentStateAdaper(getSupportFragmentManager(), stringList, fragmentList));
        tab.setupWithViewPager(v4_vp);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RootApp.mLocationClient.stop();
        JLog.w("onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        RootApp.mLocationClient.stop();
        JLog.w("onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        RootApp.mLocationClient.stop();
        JLog.w("onPause");
    }
}
