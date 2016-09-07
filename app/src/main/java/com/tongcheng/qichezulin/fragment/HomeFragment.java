package com.tongcheng.qichezulin.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiongbull.jlog.JLog;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.tongcheng.qichezulin.Param.ParamBanner;
import com.tongcheng.qichezulin.Param.ParamHotCar;
import com.tongcheng.qichezulin.Param.ParamOrderList;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.activity.CarDetailActivity;
import com.tongcheng.qichezulin.activity.FindCarTypeActivity;
import com.tongcheng.qichezulin.activity.MyOrderActivity;
import com.tongcheng.qichezulin.activity.WangDianSearchActivity;
import com.tongcheng.qichezulin.activity.ZuCheActivity;
import com.tongcheng.qichezulin.holder.NetworkImageHolderView;
import com.tongcheng.qichezulin.holder.XingChengHolderView;
import com.tongcheng.qichezulin.listner.MyLocationListener;
import com.tongcheng.qichezulin.model.BannerModel;
import com.tongcheng.qichezulin.model.CarModel;
import com.tongcheng.qichezulin.model.JsonBase;
import com.tongcheng.qichezulin.model.JsonBase2;
import com.tongcheng.qichezulin.model.OrderModel;
import com.tongcheng.qichezulin.pulltorefresh.PullToRefreshLayout;
import com.tongcheng.qichezulin.pulltorefresh.PullToRefreshLayout2ToMain;
import com.tongcheng.qichezulin.utils.Utils;
import com.tongcheng.qichezulin.utils.UtilsJson;
import com.tongcheng.qichezulin.utils.UtilsTiaoZhuang;
import com.tongcheng.qichezulin.utils.UtilsUser;
import com.tongcheng.qichezulin.view.ListViewForScrollView2;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;

/**
 * Created by 林尧 on 2016/7/22.
 * 首页片段
 */

@ContentView(R.layout.fragment_home)
public class HomeFragment extends HomeBaseFragment2 implements OnItemClickListener {


    private String token;

    // 上下拉控件
    @ViewInject(R.id.refresh_view)
    PullToRefreshLayout2ToMain refresh_view;

    //平台的公告轮播
    @ViewInject(R.id.convenientBanner)
    ConvenientBanner convenientBanner;
    List<BannerModel> bannerModels;

    //平台行程轮播
    @ViewInject(R.id.convenientBanner2)
    ConvenientBanner convenientBanner2;
    List<OrderModel> orderModels;

    //一键租车
    @ViewInject(R.id.iv_first)
    ImageView iv_first;

    //网点查询
    @ViewInject(R.id.iv_second)
    ImageView iv_second;

    //车型查询
    @ViewInject(R.id.iv_third)
    ImageView iv_third;


    // 行程部分
    @ViewInject(R.id.prl_xingcheng)
    PercentRelativeLayout prl_xingcheng;

    //热门车列表
    @ViewInject(R.id.lv_fs)
    ListViewForScrollView2 lv_fs;
    QuickAdapter<CarModel> adapter;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        JLog.w("onViewCreated");
        setPullListenerOnPullToRefreshLayout();
        setOnClickListenerOnView();
        // 第一次进入自动刷新
       /* if (isFirstIn) {
            refresh_view.autoRefresh();
            isFirstIn = false;
        }*/
        final UtilsUser utilsUser = new UtilsUser(getActivity());
        token = utilsUser.getToken_lqs();
        if (!token.equals("")) {
            //获取成功
            JLog.w("token获取成功");
            get_order_zu_lin_list(token);
        } else {
            utilsUser.init_Callback_getToken(new UtilsUser.Callback_getToken() {
                @Override
                public void start() {
                    //tokentime超时，重网络获取
                    JLog.w("tokentime超时，重网络获取token,tokentime");
                    token = utilsUser.getToken_lqs();
                    JLog.w(token);
                    get_order_zu_lin_list(token);
                }
            });
        }
        getbanner();
        do_get_hot_cars();

    }

    @Override
    void setPullListenerOnPullToRefreshLayout() {
        refresh_view.setOnPullListener(this);
        // 设置带gif动画的上拉头与下拉头
   /*     try {

            refresh_view.setGifRefreshView(new GifDrawable(getResources(), R.mipmap.anim));
            refresh_view.setGifLoadmoreView(new GifDrawable(getResources(), R.mipmap.anim));
        } catch (Exception e) {

            e.printStackTrace();
        }*/
        refresh_view.setPullDownEnable(true);
    }

    @Override
    void setOnClickListenerOnView() {
        iv_first.setOnClickListener(this);
        iv_second.setOnClickListener(this);
        iv_third.setOnClickListener(this);
        prl_xingcheng.setOnClickListener(this);

    }

    @Override
    void initData() {

    }

    @Override
    void initView() {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.iv_first:
                JLog.w("一键租车");
                UtilsTiaoZhuang.ToAnotherActivity(getActivity(), ZuCheActivity.class);
                break;

            case R.id.iv_second:
                JLog.w("网点查询");
                if (MyLocationListener.latitude != null && MyLocationListener.lontitude != null) {
                    JLog.w("经度" + MyLocationListener.latitude);
                    JLog.w("纬度" + MyLocationListener.lontitude);
                    Bundle bundle = new Bundle();
                    bundle.putString("latitude", MyLocationListener.latitude);
                    bundle.putString("lontitude", MyLocationListener.lontitude);
                    bundle.putString("isZuCheActivity", "2");
                    UtilsTiaoZhuang.ToAnotherActivity(getActivity(), WangDianSearchActivity.class, bundle);
                } else {
                    JLog.w("定位失败");
                }


                break;

            case R.id.iv_third:
                JLog.w("车型查询");
                UtilsTiaoZhuang.ToAnotherActivity(getActivity(), FindCarTypeActivity.class);
                break;

            case R.id.prl_xingcheng:
                JLog.w("行程点击");
                UtilsTiaoZhuang.ToAnotherActivity(getActivity(), MyOrderActivity.class);
                break;

        }

    }


    // 获取广播轮播数据
    private void getbanner() {
        ParamBanner paramBanner = new ParamBanner();
        Callback.Cancelable cancelable
                = x.http().get(paramBanner, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {

                UtilsJson.printJsonData(result);
                Gson gson = new Gson();
                Type type = new TypeToken<JsonBase<ArrayList<BannerModel>>>() {
                }.getType();
                JsonBase<ArrayList<BannerModel>> base = gson
                        .fromJson(result, type);
                JLog.w(base.data.size() + "");
                if (base.data != null) {
                    if (base.data.size() > 0) {
                        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                            @Override
                            public NetworkImageHolderView createHolder() {
                                return new NetworkImageHolderView();
                            }
                        }, base.data).setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
                                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                                .setOnItemClickListener(HomeFragment.this).startTurning(5000);
                        bannerModels = base.data;
                        prl_xingcheng.setVisibility(View.VISIBLE);
                    }
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

    ////
    // 获取轮播的数据
    @Override
    public void onItemClick(int position) {
        JLog.w("点击了第" + position + "张轮播图");
        if (bannerModels != null) {
            if (bannerModels.size() > 0) {
                BannerModel model = bannerModels.get(position);
                JLog.w("获取到的数据" + model.PID);
            }
        }

    }


    //获取热门车型 优惠车型
    public void do_get_hot_cars() {
        ParamHotCar paramHotCar = new ParamHotCar();
        Callback.Cancelable cancelable
                = x.http().get(paramHotCar, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {

                UtilsJson.printJsonData(result);
                Gson gson = new Gson();
                Type type = new TypeToken<JsonBase<ArrayList<CarModel>>>() {
                }.getType();
                final JsonBase<ArrayList<CarModel>> base = gson
                        .fromJson(result, type);
                JLog.w(base.data.size() + "");
                if (base.data != null) {
                    if (base.data.size() > 0) {
                        adapter = new QuickAdapter<CarModel>(getActivity(), R.layout.listview_item_car, base.data) {
                            @Override
                            protected void convert(BaseAdapterHelper helper, final CarModel item) {
                                final int position = helper.getPosition();
                                if (base.data.get(position).FType.equals("1")) {
                                    //   helper.setText(R.id.tv_re_men_or_you_hui, "热门");
                                    helper.setImageResource(R.id.iv_re_men_or_you_hui, R.mipmap.hot2x);

                                } else {
                                    helper.setImageResource(R.id.iv_re_men_or_you_hui, R.mipmap.youhui2x);
                                }
                                helper.setText(R.id.tv_car_name, item.FCarName)
                                        .setText(R.id.tv_car_price, "¥" + item.FDayMoney + "/")
                                        .setText(R.id.tv_car_remark, item.FRemark)
                                        .setImageUrl(R.id.iv_car_picture, item.FImg);
                                helper.getView(R.id.rrl_item).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        JLog.w("汽车的id===========" + item.KCarID);
                                        UtilsTiaoZhuang.ToAnotherActivity(getActivity(), CarDetailActivity.class, UtilsTiaoZhuang.EveryCar(item));

                                    }
                                });
                            }
                        };
                        lv_fs.setAdapter(adapter);
                    }
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

    @Override
    public void onRefresh(PullToRefreshLayout2ToMain pullToRefreshLayout) {
        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout2ToMain pullToRefreshLayout) {
        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
    }


    //获取租赁中的订单数据
    public void get_order_zu_lin_list(String token) {
        ParamOrderList paramOrderList = new ParamOrderList();
        paramOrderList.user_id = UtilsUser.getUserID(getActivity());
        paramOrderList.token=token;
        paramOrderList.status = "2";
        paramOrderList.page = "1";
        paramOrderList.page_size = "10";
        Callback.Cancelable cancelable
                = x.http().post(paramOrderList, new Callback.CommonCallback<String>() {
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
                    Type type = new TypeToken<JsonBase2<List<OrderModel>>>() {
                    }.getType();
                    JsonBase2<List<OrderModel>> base = gson
                            .fromJson(result, type);
                    if (!base.status.toString().trim().equals("0")) {
                        if (base.data != null) {
                            JLog.w("获取租赁中的订单成功");
                            if (base.data.size() > 0) {
                                convenientBanner2.startTurning(10000);
                                //convenientBanner2.setScrollDuration(10000);
                                convenientBanner2.setPages(new CBViewHolderCreator<XingChengHolderView>() {
                                    @Override
                                    public XingChengHolderView createHolder() {
                                        return new XingChengHolderView();
                                    }
                                }, base.data).setClickable(false);
                                orderModels = base.data;
                            }

                        }
                    } else {
                        JLog.w("获取租赁中的订单失败");
                    }
                } catch (Exception E) {
                    E.printStackTrace();
                }

            }
        });


    }

}
