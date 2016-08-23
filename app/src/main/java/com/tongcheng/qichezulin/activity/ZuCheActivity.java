package com.tongcheng.qichezulin.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
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
import com.tongcheng.qichezulin.Param.ParamChooseCar;
import com.tongcheng.qichezulin.Param.ParamGetCarType;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.listner.MyLocationListener;
import com.tongcheng.qichezulin.model.CarModel3;
import com.tongcheng.qichezulin.model.CarTypeModel;
import com.tongcheng.qichezulin.model.JsonBase;
import com.tongcheng.qichezulin.model.JsonBase2;
import com.tongcheng.qichezulin.utils.Utils;
import com.tongcheng.qichezulin.utils.UtilsDate;
import com.tongcheng.qichezulin.utils.UtilsJson;
import com.tongcheng.qichezulin.utils.UtilsTiaoZhuang;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
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

    @ViewInject(R.id.btn_help_xuan_che) //帮我选车按钮
            Button btn_help_xuan_che;
    @ViewInject(R.id.btn_xuan_che) //我要选车按钮
            Button btn_xuan_che;
    @ViewInject(R.id.tv_hour_zu) //时租金
            TextView tv_hour_zu;
    @ViewInject(R.id.tv_day_zu) //日租金
            TextView tv_day_zu;
    @ViewInject(R.id.tv_month_zu) //月租金
            TextView tv_month_zu;
    @ViewInject(R.id.tv_low) //最低值
            TextView tv_low;
    @ViewInject(R.id.tv_max) //最高值
            TextView tv_max;
    @ViewInject(R.id.tv_shi_zu_jin) //显示租金范围
            TextView tv_shi_zu_jin;
    @ViewInject(R.id.dsb_DiscreteSeekBar)
    DiscreteSeekBar dsb_DiscreteSeekBar;
    @ViewInject(R.id.iv_record)
    ImageView iv_record;
    @ViewInject(R.id.iv_help)
    ImageView iv_help;
    @ViewInject(R.id.tv_shop_name) //取车门店
            TextView tv_shop_name;
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
    private int type = 0;//标注租车的时间类型
    private String cartype = "";
    private String shop_id;//存放选中的门店id

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
                    bundle.putString("isZuCheActivity", "1");
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
            case R.id.tv_hour_zu:
                tv_low.setText("0");
                tv_max.setText("300");
                tv_hour_zu.setTextColor(getResources().getColor(R.color.green_36b57e));
                tv_hour_zu.setBackground(getResources().getDrawable(R.drawable.shape3));
                tv_day_zu.setTextColor(getResources().getColor(R.color.gray9999999));
                tv_day_zu.setBackground(getResources().getDrawable(R.drawable.shape4));
                tv_month_zu.setBackground(getResources().getDrawable(R.drawable.shape4));
                tv_month_zu.setTextColor(getResources().getColor(R.color.gray9999999));
                dsb_DiscreteSeekBar.setMax(300);
                dsb_DiscreteSeekBar.setMin(0);
                dsb_DiscreteSeekBar.setProgress(40);
                break;
            case R.id.tv_day_zu:
                tv_low.setText("0");
                tv_max.setText("4500");
                tv_hour_zu.setTextColor(getResources().getColor(R.color.gray9999999));
                tv_hour_zu.setBackground(getResources().getDrawable(R.drawable.shape4));
                tv_day_zu.setTextColor(getResources().getColor(R.color.green_36b57e));
                tv_day_zu.setBackground(getResources().getDrawable(R.drawable.shape3));
                tv_month_zu.setBackground(getResources().getDrawable(R.drawable.shape4));
                tv_month_zu.setTextColor(getResources().getColor(R.color.gray9999999));
                dsb_DiscreteSeekBar.setMax(4500);
                dsb_DiscreteSeekBar.setMin(0);
                dsb_DiscreteSeekBar.setProgress(100);
                break;
            case R.id.tv_month_zu:
                tv_low.setText("0");
                tv_max.setText("30000");
                tv_hour_zu.setTextColor(getResources().getColor(R.color.gray9999999));
                tv_hour_zu.setBackground(getResources().getDrawable(R.drawable.shape4));
                tv_day_zu.setBackground(getResources().getDrawable(R.drawable.shape4));
                tv_day_zu.setTextColor(getResources().getColor(R.color.gray9999999));
                tv_month_zu.setBackground(getResources().getDrawable(R.drawable.shape3));
                tv_month_zu.setTextColor(getResources().getColor(R.color.green_36b57e));
                dsb_DiscreteSeekBar.setMax(30000);
                dsb_DiscreteSeekBar.setMin(0);
                dsb_DiscreteSeekBar.setProgress(3000);
                break;

            case R.id.btn_help_xuan_che:
                if (type == 0) {
                    Utils.ShowText2(ZuCheActivity.this, "请先确定好租车时间");
                } else if (shop_id == null || shop_id == "") {
                    Utils.ShowText2(ZuCheActivity.this, "请先选择取车门店");
                } else {
                    JLog.w(dsb_DiscreteSeekBar.getProgress() + "");
                    getHelpMeCar(shop_id, "1", dsb_DiscreteSeekBar.getProgress() + "", "0", type + "", cartype);
                }

                break;
            case R.id.btn_xuan_che:
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
                    int i = UtilsDate.getTimeBetween3(back_car_date, get_car_date, ZuCheActivity.this, textView1, textView2, textView3);
                    change_css(i);
                    type = i;
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
                    int i = UtilsDate.getTimeBetween2(get_car_date, back_car_date, ZuCheActivity.this, textView1, textView2, textView3);
                    change_css(i);
                    type = i;
                }
            }
        });
    }

    public void initView() {
        UtilsDate.getDate2(tv_left_date);
        UtilsDate.getTime2(tv_left_time);
        UtilsDate.getTomorrow(tv_Right_date, tv_Right_time);
        dsb_DiscreteSeekBar.setIndicatorPopupEnabled(true);
        dsb_DiscreteSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {

                JLog.w(value + "");
                tv_shi_zu_jin.setText("租金:0~" + value);
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });

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

        tv_hour_zu.setOnClickListener(this);
        tv_day_zu.setOnClickListener(this);
        tv_month_zu.setOnClickListener(this);

        btn_help_xuan_che.setOnClickListener(this);
        btn_xuan_che.setOnClickListener(this);
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
                        cartype = item.PID;
                        JLog.w(item.PID);
                        helper.setTextColor(R.id.btn_show_car_type, getResources().getColor(R.color.whiteFFFFFF));
                        helper.getView(R.id.btn_show_car_type).setBackgroundResource(R.drawable.shape7);
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
        if (WangDianSearchActivity.shop != null) {
            JLog.w(WangDianSearchActivity.shop.FShopName);
            tv_shop_name.setText(WangDianSearchActivity.shop.FShopName);
            shop_id = WangDianSearchActivity.shop.PID;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JLog.w("onPause");
    }


    //帮我选车 获取一条数据
    public void getHelpMeCar(String shop_id, String is_auto, String max_price, String min_price, String paytype, String type) {

        ParamChooseCar paramChooseCar = new ParamChooseCar();
        paramChooseCar.shop_id = shop_id;
        paramChooseCar.is_auto = is_auto;
        paramChooseCar.max_price = max_price;
        paramChooseCar.min_price = min_price;
        paramChooseCar.paytype = paytype;
        paramChooseCar.type = type;
        Callback.Cancelable cancelable
                = x.http().post(paramChooseCar, new Callback.CommonCallback<String>() {
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
                    Type type = new TypeToken<JsonBase2<List<CarModel3>>>() {
                    }.getType();
                    JsonBase2<List<CarModel3>> base = gson
                            .fromJson(result, type);
                    if (!base.status.toString().trim().equals("0")) {
                        if (base.data != null) {
                            if (base.data.size() == 1) {
                                JLog.w("获取帮我选车成功");
                                Bundle bundle = new Bundle();
                                bundle.putString("days", tv_show_jian_ge.getText() + "");
                                bundle.putSerializable("obj", base.data.get(0));
                                UtilsTiaoZhuang.ToAnotherActivity(ZuCheActivity.this, YuYueActivity.class, bundle);
                            } else if (base.data.size() > 1) {
                                //跳到选车列表
                            } else if (base.data.size() == 0) {
                                Utils.ShowText2(ZuCheActivity.this, "不好意思,选不到车");
                            }


                        }
                    } else {
                        JLog.w("获取帮我选车失败");
                    }
                } catch (Exception E) {
                    E.printStackTrace();
                }

            }
        });

    }

    //我要选车  获取多条数据
    public void getManyCars() {
        UtilsTiaoZhuang.ToAnotherActivity(ZuCheActivity.this, FindCarTypeActivity.class);
    }

    public void change_css(int i) {
        if (i == 1) {
            tv_low.setText("0");
            tv_max.setText("300");
            tv_hour_zu.setTextColor(getResources().getColor(R.color.green_36b57e));
            tv_hour_zu.setBackground(getResources().getDrawable(R.drawable.shape3));
            tv_day_zu.setTextColor(getResources().getColor(R.color.gray9999999));
            tv_day_zu.setBackground(getResources().getDrawable(R.drawable.shape4));
            tv_month_zu.setBackground(getResources().getDrawable(R.drawable.shape4));
            tv_month_zu.setTextColor(getResources().getColor(R.color.gray9999999));
            dsb_DiscreteSeekBar.setMax(300);
            dsb_DiscreteSeekBar.setMin(0);
            dsb_DiscreteSeekBar.setProgress(40);
        } else if (i == 2) {
            tv_low.setText("0");
            tv_max.setText("4500");
            tv_hour_zu.setTextColor(getResources().getColor(R.color.gray9999999));
            tv_hour_zu.setBackground(getResources().getDrawable(R.drawable.shape4));
            tv_day_zu.setTextColor(getResources().getColor(R.color.green_36b57e));
            tv_day_zu.setBackground(getResources().getDrawable(R.drawable.shape3));
            tv_month_zu.setBackground(getResources().getDrawable(R.drawable.shape4));
            tv_month_zu.setTextColor(getResources().getColor(R.color.gray9999999));
            dsb_DiscreteSeekBar.setMax(4500);
            dsb_DiscreteSeekBar.setMin(0);
            dsb_DiscreteSeekBar.setProgress(100);
        } else if (i == 3) {
            tv_low.setText("0");
            tv_max.setText("30000");
            tv_hour_zu.setTextColor(getResources().getColor(R.color.gray9999999));
            tv_hour_zu.setBackground(getResources().getDrawable(R.drawable.shape4));
            tv_day_zu.setBackground(getResources().getDrawable(R.drawable.shape4));
            tv_day_zu.setTextColor(getResources().getColor(R.color.gray9999999));
            tv_month_zu.setBackground(getResources().getDrawable(R.drawable.shape3));
            tv_month_zu.setTextColor(getResources().getColor(R.color.green_36b57e));
            dsb_DiscreteSeekBar.setMax(30000);
            dsb_DiscreteSeekBar.setMin(0);
            dsb_DiscreteSeekBar.setProgress(3000);
        }
    }
}
