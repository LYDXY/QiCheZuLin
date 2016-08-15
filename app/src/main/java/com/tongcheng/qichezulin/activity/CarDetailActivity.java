package com.tongcheng.qichezulin.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.utils.UtilsTiaoZhuang;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by 林尧 on 2016/8/10.
 */

@ContentView(R.layout.activity_car_detail)
public class CarDetailActivity extends PuTongActivity {


    Bundle bundle;

    @ViewInject(R.id.tv_user_this_car) //车大图
            TextView tv_user_this_car;

    @ViewInject(R.id.tv_this_car_name) //车名
            TextView tv_this_car_name;

    @ViewInject(R.id.tv_power) //动力
            TextView tv_power;

    @ViewInject(R.id.tv_zi_dong) //自动/手动
            TextView tv_zi_dong;

    @ViewInject(R.id.tv_car_type_show) //混合动力/纯电动
            TextView tv_car_type_show;

    @ViewInject(R.id.tv_zuo_number) //座位数量
            TextView tv_zuo_number;

    @ViewInject(R.id.tv_is_have_dao_hang) //车载导航
            TextView tv_is_have_dao_hang;


    @ViewInject(R.id.tv_tian_chuang) //是否有天窗
            TextView tv_tian_chuang;

    @ViewInject(R.id.tv_show_cat_price) //日均价 /月租价
            TextView tv_show_cat_price;


    @ViewInject(R.id.iv_this_car)
    ImageView iv_this_car;

    @Override
    void initData() {


    }

    @Override
    void initView() {
        bundle = getIntent().getExtras();
        try {


            bundle.getString("FDayMoney");
            bundle.getString("FType");
            bundle.getString("KCarID");
            bundle.getString("FRemark");
            bundle.getString("PID");
            bundle.getString("KTypeID");
            bundle.getString("FCreateDate");
            bundle.getString("FHourMoney");
            bundle.getString("FMonthMoney");
            bundle.getString("FPower");
            bundle.getString("FIsNavigation");
            bundle.getString("FIsCarWindows");
            bundle.getString("FState");
            bundle.getString("FBond");
            x.image().bind(iv_this_car, bundle.getString("FImg"));
            tv_this_car_name.setText("<" + bundle.getString("FCarName") + ">");
            tv_power.setText(bundle.getString("FPower") + "升");
            if (bundle.getString("FIsAutomatic").equals("1")) {
                tv_zi_dong.setText("手动");
            } else {
                tv_zi_dong.setText("自动");
            }

            if (bundle.getString("FDriveMethod").equals("1")) {
                tv_car_type_show.setText("燃油");
            } else if (bundle.getString("FDriveMethod").equals("2")) {
                tv_car_type_show.setText("混合动力");
            } else {
                tv_car_type_show.setText("纯电动");
            }
            if (bundle.getString("FIsNavigation").equals("true")) {
                tv_is_have_dao_hang.setText("车载导航");
            } else {
                tv_is_have_dao_hang.setText("没有车载");
            }

            if (bundle.getString("FIsCarWindows").equals("true")) {
                tv_tian_chuang.setText("天窗");
            } else {
                tv_tian_chuang.setText("没有天窗");
            }
            tv_zuo_number.setText(bundle.getString("FSeat") + "座");
            tv_show_cat_price.setText("¥" + bundle.getString("FDayMoney") + "/日均  ¥" + bundle.getString("FMonthMoney") + "/月租价");
        } catch (Exception E) {
            E.printStackTrace();
        }


        tv_user_this_car.setOnClickListener(this);
        tv_first.setVisibility(View.VISIBLE);
        tv_second.setVisibility(View.VISIBLE);
        tv_first.setText("车辆详情");
        tv_second.setText("更换条件");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_return:
                onBackPressed();
                break;
            case R.id.tv_second:
                JLog.w("租车界面");
                UtilsTiaoZhuang.ToAnotherActivity(CarDetailActivity.this, ZuCheActivity.class);
                break;
            case R.id.tv_user_this_car:
                JLog.w("租用这辆车");
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListenerOnView();
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
