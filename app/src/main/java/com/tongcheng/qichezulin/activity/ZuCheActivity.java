package com.tongcheng.qichezulin.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.bigkoo.alertview.OnItemClickListener;
import com.bigkoo.pickerview.TimePickerView;
import com.code19.library.DateUtils;
import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.listner.MyLocationListener;
import com.tongcheng.qichezulin.utils.Utils;
import com.tongcheng.qichezulin.utils.UtilsDate;
import com.tongcheng.qichezulin.utils.UtilsTiaoZhuang;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by 林尧 on 2016/7/22.
 */

@ContentView(R.layout.activity_zu_che)
public class ZuCheActivity extends Activity implements View.OnClickListener, OnItemClickListener, OnDismissListener {


    AlertView mAlertViewExt;//窗口拓展例子
    @ViewInject(R.id.rrl_third)
    PercentRelativeLayout rrl_third;
    @ViewInject(R.id.rrl_second)
    PercentRelativeLayout rrl_second;


    TimePickerView pvTime; //时间选择控件

    @ViewInject(R.id.tv_left_date)
    TextView tv_left_date;

    @ViewInject(R.id.tv_left_time)
    TextView tv_left_time;

    @ViewInject(R.id.tv_Right_date)
    TextView tv_Right_date;
    @ViewInject(R.id.tv_Right_time)
    TextView tv_Right_time;

    @ViewInject(R.id.iv_return)
    ImageView iv_return;

    @ViewInject(R.id.tv_show_jian_ge)
    TextView tv_show_jian_ge;
    //选中后的取车时间
    Date get_car_date, back_car_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setClickListnerOnView();
        initView();
        setClickListner();
    }


    private void setClickListnerOnView() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_left_date:
                show_left_time(tv_left_date, tv_left_time, tv_show_jian_ge);
                break;
            case R.id.tv_left_time:
                show_left_time(tv_left_date, tv_left_time, tv_show_jian_ge);
                break;
            case R.id.tv_Right_date:
                show_Right_time(tv_Right_date, tv_Right_time, tv_show_jian_ge);
                break;
            case R.id.tv_Right_time:
                show_Right_time(tv_Right_date, tv_Right_time, tv_show_jian_ge);
                break;
            case R.id.iv_return:
                onBackPressed();
                break;
            case R.id.rrl_second:
                if (MyLocationListener.latitude != null && MyLocationListener.lontitude != null) {
                    JLog.i("经度" + MyLocationListener.latitude);
                    JLog.i("纬度" + MyLocationListener.lontitude);
                    Bundle bundle = new Bundle();
                    bundle.putString("latitude", MyLocationListener.latitude);
                    bundle.putString("lontitude", MyLocationListener.lontitude);
                    UtilsTiaoZhuang.ToAnotherActivity(this, WangDianSearchActivity.class, bundle);
                } else {
                    JLog.i("定位失败");
                }

                break;
            case R.id.rrl_third:
                showAlertView();
                break;

        }
    }

    @Override
    public void onDismiss(Object o) {

    }

    @Override
    public void onItemClick(Object o, int position) {

    }


    //弹出时间选择器---选择取车时间
    private void show_left_time(final TextView textView1, final TextView textView2, final TextView textView3) {
        pvTime = new TimePickerView(this, TimePickerView.Type.ALL);
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR) + 2);//要在setTime 之前才有效果哦
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        pvTime.show();
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                get_car_date = date;
                if (back_car_date == null) {
                    UtilsDate.getTimeBetween(date, ZuCheActivity.this, textView1, textView2);
                } else {
                    UtilsDate.getTimeBetween3(back_car_date, get_car_date, ZuCheActivity.this, textView1, textView2, textView3);
                }

            }
        });
    }


    //弹出时间选择器---选择还车时间
    private void show_Right_time(final TextView textView1, final TextView textView2, final TextView textView3) {
        pvTime = new TimePickerView(this, TimePickerView.Type.ALL);
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR) + 2);//要在setTime 之前才有效果哦
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        pvTime.show();
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                back_car_date = date;
                if (get_car_date == null) {
                    Utils.ShowText(ZuCheActivity.this, "请先选择取车时间");
                } else {
                    UtilsDate.getTimeBetween2(get_car_date, back_car_date, ZuCheActivity.this, textView1, textView2, textView3);
                }
            }
        });
    }

    public void initView() {
        UtilsDate.getDate2(tv_left_date);
        UtilsDate.getTime2(tv_left_time);
        UtilsDate.getTomorrow(tv_Right_date, tv_Right_time);

    }

    public void setClickListner() {
        tv_left_date.setOnClickListener(this);
        tv_left_time.setOnClickListener(this);
        tv_Right_date.setOnClickListener(this);
        tv_Right_time.setOnClickListener(this);
        iv_return.setOnClickListener(this);
        rrl_third.setOnClickListener(this);
        rrl_second.setOnClickListener(this);
    }


    public void showAlertView() {
        mAlertViewExt = new AlertView(null, null, null, null, null, this, AlertView.Style.Alert, this);
        ViewGroup extView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.alertview00, null);
        mAlertViewExt.addExtView(extView);
        mAlertViewExt.show();
        // mAlertViewExt.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        JLog.i("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        JLog.i("onPause");
    }
}
