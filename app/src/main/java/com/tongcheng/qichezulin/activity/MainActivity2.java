package com.tongcheng.qichezulin.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.utils.UnreadMsgUtils;
import com.flyco.tablayout.widget.MsgView;
import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.config.RootApp;
import com.tongcheng.qichezulin.entity.TabEntity;
import com.tongcheng.qichezulin.fragment.HomeFragment;
import com.tongcheng.qichezulin.fragment.MessageFragment;
import com.tongcheng.qichezulin.fragment.PersonFragment;
import com.tongcheng.qichezulin.fragment.WhiteFragment;
import com.tongcheng.qichezulin.listner.MyLocationListener;
import com.tongcheng.qichezulin.utils.UtilsTiaoZhuang;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by 林尧 on 2016/7/22.
 */

@ContentView(R.layout.activity_main2)
public class MainActivity2 extends PuTongFragmentActivity {


    public int lastposition;

    //个人中心图片
    @ViewInject(R.id.iv_set_up)
    ImageView iv_set_up;
    //切换的 tab
    @ViewInject(R.id.tab)
    com.flyco.tablayout.CommonTabLayout tab;
    // viewpager
  /*  @ViewInject(R.id.v4_vp)
    ViewPagerWithoutSlide v4_vp;*/
    //片段集合
    ArrayList<Fragment> fragmentList;
    //标签
    private String[] mTitles = {"首页", "附近", "消息", "个人"};

    private int[] mIconUnselectIds = {
            R.mipmap.home, R.mipmap.near,
            R.mipmap.information_46, R.mipmap.personal};
    private int[] mIconSelectIds = {
            R.mipmap.home_selected, R.mipmap.near_selectedr,
            R.mipmap.information_selected, R.mipmap.personal_selected};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JLog.w("onCreate");
        initData();
        initView();
        setListenerOnView();
        RootApp.mLocationClient.start();

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

                break;
        }
    }

    @Override
    void initData() {
        fragmentList = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        fragmentList.add(new HomeFragment());
        fragmentList.add(new WhiteFragment());
        fragmentList.add(new MessageFragment());
        fragmentList.add(new PersonFragment());
    }

    @Override
    void initView() {
        iv_return.setVisibility(View.GONE);
        tv_first.setVisibility(View.VISIBLE);
        tv_second.setVisibility(View.GONE);
        tv_first.setText("同城租车");
        //  v4_vp.setAdapter(new FragmentStateAdaper2(getSupportFragmentManager(), mTitles, fragmentList));
        tab.setTabData(mTabEntities, this, R.id.fl_change, fragmentList);
        //设置未读消息红点 -----------根据后台数据判断
        tab.showDot(2);
        MsgView rtv_2_2 = tab.getMsgView(2);
        if (rtv_2_2 != null) {
            UnreadMsgUtils.setSize(rtv_2_2, dp2px(7.5f));
        }
        ///////////////////////////////

        tab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position == 0) {
                    tv_first.setText("同城租车");
                    tv_second.setVisibility(View.GONE);
                    iv_set_up.setVisibility(View.GONE);
                } else if (position == 1) {
                    UtilsTiaoZhuang.ToAnotherActivity(MainActivity2.this, WangDianActivity.class);
                    JLog.w(MyLocationListener.latitude + "./......纬度.");
                    JLog.w(MyLocationListener.lontitude + "./......纬度.");
                } else if (position == 2) {
                    tv_first.setText("消息");
                    tv_second.setVisibility(View.GONE);
                    iv_set_up.setVisibility(View.GONE);
                } else if (position == 3) {
                    tv_first.setText("个人中心");
                    tv_second.setVisibility(View.GONE);
                    iv_set_up.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        //   v4_vp.setCurrentItem(0);

     /*   v4_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tab.setCurrentTab(position);
                if (position == 0) {
                    tv_first.setText("同城租车");
                    tv_second.setVisibility(View.GONE);
                    iv_set_up.setVisibility(View.GONE);
                } else if (position == 1) {
                    tv_first.setText("附近");
                    tv_second.setVisibility(View.GONE);
                    iv_set_up.setVisibility(View.GONE);
                    JLog.w(MyLocationListener.latitude + "./......纬度.");
                    JLog.w(MyLocationListener.lontitude + "./......纬度.");
                } else if (position == 2) {
                    tv_first.setText("消息");
                    tv_second.setVisibility(View.GONE);
                    iv_set_up.setVisibility(View.GONE);
                } else if (position == 3) {
                    tv_first.setText("个人中心");
                    tv_second.setVisibility(View.GONE);
                    iv_set_up.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/
    }


    protected int dp2px(float dp) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    protected void onStop() {
        super.onStop();
        JLog.w("onStop");

    }

    @Override
    protected void onPause() {
        super.onPause();
        JLog.w("onPause");

    }

    @Override
    protected void onDestroy() {
        RootApp.mLocationClient.stop();
        super.onDestroy();
        JLog.w("onDestroy");

    }

    @Override
    protected void onResume() {
        super.onResume();
        JLog.w("onResume");
        tab.setCurrentTab(0);
    }






}
