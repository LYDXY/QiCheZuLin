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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.bigkoo.alertview.OnItemClickListener;
import com.bigkoo.pickerview.TimePickerView;
import com.code19.library.DateUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiongbull.jlog.JLog;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.tongcheng.qichezulin.Param.ParamGetCarType;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.listner.MyLocationListener;
import com.tongcheng.qichezulin.model.CarTypeModel;
import com.tongcheng.qichezulin.model.JsonBase;
import com.tongcheng.qichezulin.utils.Utils;
import com.tongcheng.qichezulin.utils.UtilsDate;
import com.tongcheng.qichezulin.utils.UtilsJson;
import com.tongcheng.qichezulin.utils.UtilsTiaoZhuang;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by 林尧 on 2016/7/22.
 */

@ContentView(R.layout.activity_zu_che)
public class ZuCheActivity extends Activity implements View.OnClickListener, OnItemClickListener, OnDismissListener {


    @ViewInject(R.id.iv_record)
    ImageView iv_record;
    @ViewInject(R.id.iv_help)
    ImageView iv_help;


    @ViewInject(R.id.tv_car_type_show)
    TextView tv_car_type_show;
    AlertView mAlertViewExt;//窗口拓展例子
    @ViewInject(R.id.rrl_third)
    PercentRelativeLayout rrl_third;
    @ViewInject(R.id.rrl_second)
    PercentRelativeLayout rrl_second;
    Adapter adapter;

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
                    JLog.w("经度" + MyLocationListener.latitude);
                    JLog.w("纬度" + MyLocationListener.lontitude);
                    Bundle bundle = new Bundle();
                    bundle.putString("latitude", MyLocationListener.latitude);
                    bundle.putString("lontitude", MyLocationListener.lontitude);
                    UtilsTiaoZhuang.ToAnotherActivity(this, WangDianSearchActivity.class, bundle);
                } else {
                    JLog.w("定位失败");
                }

                break;
            case R.id.rrl_third:
                get_Data_in_AlertView();
                break;
            case R.id.iv_record:
                UtilsTiaoZhuang.ToAnotherActivity(this, MenDianRecordActivity.class);
                break;
            case R.id.iv_help:
                UtilsTiaoZhuang.ToAnotherActivity(this, FeiYongShuoMingActivity.class);
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
        iv_record.setOnClickListener(this);
        iv_help.setOnClickListener(this);
    }


    public void showAlertView(final List<CarTypeModel> carTypeModels) {
        mAlertViewExt = new AlertView(null, null, null, null, null, this, AlertView.Style.Alert, this);
        ViewGroup view = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.alertview00, null);
        GridView gridView = (GridView) view.findViewById(R.id.gv_list);
        adapter = new Adapter<CarTypeModel>(getApplication(), R.layout.gv_item00) {
            @Override
            protected void convert(final AdapterHelper helper, final CarTypeModel item) {
                final int position = helper.getPosition();
                helper
                        .setText(R.id.btn_show_car_type, item.FTypeName)
                        .getView(R.id.btn_show_car_type).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        JLog.w(item.PID);
                        mAlertViewExt.dismiss();
                        tv_car_type_show.setText(item.FTypeName);
                    }
                });
            }
        };
        adapter.addAll(carTypeModels);
        gridView.setAdapter(adapter);
        mAlertViewExt.addExtView(view);
        mAlertViewExt.show();
    }


    public void get_Data_in_AlertView() {
        ParamGetCarType paramGetCarType = new ParamGetCarType();
        Callback.Cancelable cancelable
                = x.http().post(paramGetCarType, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                UtilsJson.printJsonData(result);
                Gson gson = new Gson();
                Type type = new TypeToken<JsonBase<ArrayList<CarTypeModel>>>() {
                }.getType();
                JsonBase<ArrayList<CarTypeModel>> base = gson
                        .fromJson(result, type);
                if (!base.status.toString().trim().equals("0")) {
                    if (base.data.size() > 0) {
                        showAlertView(base.data);
                    }
                } else {
                    JLog.w(base.info);

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
    protected void onResume() {
        super.onResume();
        JLog.w("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        JLog.w("onPause");
    }
}
