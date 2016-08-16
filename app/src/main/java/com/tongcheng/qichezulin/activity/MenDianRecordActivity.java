package com.tongcheng.qichezulin.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiongbull.jlog.JLog;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.tongcheng.qichezulin.Param.ParamGetAllCar;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.model.CarModel2;
import com.tongcheng.qichezulin.model.JsonBase;
import com.tongcheng.qichezulin.pulltorefresh.PullToRefreshLayout;
import com.tongcheng.qichezulin.pulltorefresh.PullableListView;
import com.tongcheng.qichezulin.utils.Utils;
import com.tongcheng.qichezulin.utils.UtilsJson;
import com.tongcheng.qichezulin.utils.UtilsTiaoZhuang;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifDrawable;

/**
 * Created by 林尧 on 2016/8/10.
 */

@ContentView(R.layout.activity_find_car_type)
public class MenDianRecordActivity extends PuTongActivity2 {


    @ViewInject(R.id.prl_prl) //下拉刷新控件
            PullToRefreshLayout prl_prl;
    @ViewInject(R.id.plv_car_list)
    PullableListView plv_car_list; //listview
    Adapter<CarModel2> catModel2Adapter;


    private boolean isFirstIn = true;


    @Override
    void initData() {

    }

    @Override
    void initView() {
        tv_first.setVisibility(View.VISIBLE);
        tv_first.setText("门店记录");
        try {
            prl_prl.setGifRefreshView(new GifDrawable(getResources(), R.mipmap.anim));
            prl_prl.setGifLoadmoreView(new GifDrawable(getResources(), R.mipmap.anim));

        } catch (Resources.NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_return:
                onBackPressed();
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListenerOnView();
        setOnPullListenerOnprl_prl();
        do_get_data();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // 第一次进入自动刷新
        if (isFirstIn) {
            prl_prl.autoRefresh();
            isFirstIn = false;
        }
    }

    public void setOnPullListenerOnprl_prl() {
        prl_prl.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        });
    }


    // 获取列表数据
    private void do_get_data() {
        ParamGetAllCar paramGetAllCar = new ParamGetAllCar();
        // paramGetAllCar.max_price = "";
        // paramGetAllCar.min_price = "";
        paramGetAllCar.page = "1";
        // paramGetAllCar.page_size = "";
        //   paramGetAllCar.cartype = "";
        //   paramGetAllCar.paytype = "";
        Callback.Cancelable cancelable
                = x.http().post(paramGetAllCar, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    UtilsJson.printJsonData(result);
                    Gson gson = new Gson();
                    Type type = new TypeToken<JsonBase<ArrayList<CarModel2>>>() {
                    }.getType();
                    JsonBase<ArrayList<CarModel2>> base = gson
                            .fromJson(result, type);
                    if (!base.status.toString().trim().equals("0")) {
                        if (base.data != null) {
                            JLog.w(base.data.size() + "");
                            if (base.data.size() > 0) {
                                JLog.w("获取数据成功");
                                if (catModel2Adapter == null) {
                                    catModel2Adapter = new Adapter<CarModel2>(MenDianRecordActivity.this, R.layout.listview_item_car2) {
                                        @Override
                                        protected void convert(AdapterHelper helper, final CarModel2 item) {
                                            final int position = helper.getPosition();
                                            JLog.w(position + "");
                                            helper.setImageUrl(R.id.iv_car_picture, item.FImg)
                                                    .setText(R.id.tv_car_name, item.FCarName)
                                                    .setText(R.id.tv_car_remark, item.FRemark)
                                                    .setText(R.id.tv_car_price, "¥" + item.FDayMoney + "/")
                                                    .setText(R.id.tv_car_yue_price, "¥" + item.FMonthMoney + "/")
                                                    .setVisible(R.id.pll_is_zu_man, View.GONE)
                                                    .setVisible(R.id.line1233, View.GONE)
                                                    .getView(R.id.rrl_item).setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    UtilsTiaoZhuang.ToAnotherActivity(MenDianRecordActivity.this, CarDetailActivity.class, UtilsTiaoZhuang.EveryCar(item));
                                                }
                                            });
                                        }
                                    };
                                    catModel2Adapter.addAll(base.data);
                                    plv_car_list.setAdapter(catModel2Adapter);
                                } else {
                                    catModel2Adapter.addAll(base.data);
                                    catModel2Adapter.notifyAll();
                                }
                            }
                        }
                    } else {
                        Utils.ShowText(MenDianRecordActivity.this, base.info.toString());
                    }
                } catch (Exception E) {

                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                JLog.w(isOnCallback + "");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                JLog.w(cex + "");
            }

            @Override
            public void onFinished() {

            }
        });
    }
}
