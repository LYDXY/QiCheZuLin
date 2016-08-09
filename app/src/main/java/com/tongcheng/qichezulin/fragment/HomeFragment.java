package com.tongcheng.qichezulin.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiongbull.jlog.JLog;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.tongcheng.qichezulin.Param.ParamBanner;
import com.tongcheng.qichezulin.Param.ParamHotCar;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.activity.WangDianSearchActivity;
import com.tongcheng.qichezulin.activity.ZuCheActivity;
import com.tongcheng.qichezulin.holder.NetworkImageHolderView;
import com.tongcheng.qichezulin.listner.MyLocationListener;
import com.tongcheng.qichezulin.model.BannerModel;
import com.tongcheng.qichezulin.model.CatModel;
import com.tongcheng.qichezulin.model.JsonBase;
import com.tongcheng.qichezulin.pulltorefresh.PullToRefreshLayout;
import com.tongcheng.qichezulin.utils.UtilsJson;
import com.tongcheng.qichezulin.utils.UtilsTiaoZhuang;
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
public class HomeFragment extends PuTongFragment2 implements OnItemClickListener {


    public MyLocationListener myLocationListener = null;


    // 上下拉控件
    @ViewInject(R.id.refresh_view)
    PullToRefreshLayout refresh_view;

    //平台的公告轮播
    @ViewInject(R.id.convenientBanner)
    ConvenientBanner convenientBanner;
    List<BannerModel> bannerModels;

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
    QuickAdapter<CatModel> adapter;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        JLog.i("onViewCreated");
        setPullListenerOnPullToRefreshLayout();
        setOnClickListenerOnView();
        // 第一次进入自动刷新
        if (isFirstIn) {
            refresh_view.autoRefresh();
            isFirstIn = false;
        }
        getbanner();
        do_get_hot_cars();
    }

    @Override
    void setPullListenerOnPullToRefreshLayout() {
        refresh_view.setOnPullListener(this);
        // 设置带gif动画的上拉头与下拉头
        try {

            refresh_view.setGifRefreshView(new GifDrawable(getResources(), R.mipmap.anim));
            refresh_view.setGifLoadmoreView(new GifDrawable(getResources(), R.mipmap.anim));
        } catch (Exception e) {

            e.printStackTrace();
        }
        refresh_view.setPullDownEnable(false);
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
                JLog.i("一键租车");
                UtilsTiaoZhuang.ToAnotherActivity(getActivity(), ZuCheActivity.class);
                break;

            case R.id.iv_second:
                JLog.i("网点查询");
                if (MyLocationListener.latitude != null && MyLocationListener.lontitude != null) {
                    JLog.i("经度" + MyLocationListener.latitude);
                    JLog.i("纬度" + MyLocationListener.lontitude);
                    Bundle bundle = new Bundle();
                    bundle.putString("latitude", MyLocationListener.latitude);
                    bundle.putString("lontitude", MyLocationListener.lontitude);
                    UtilsTiaoZhuang.ToAnotherActivity(getActivity(), WangDianSearchActivity.class, bundle);
                } else {
                    JLog.i("定位失败");
                }


                break;

            case R.id.iv_third:
                JLog.i("车型查询");
                break;

            case R.id.prl_xingcheng:
                JLog.i("行程点击");
                break;

        }

    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        // 下拉刷新操作

        refresh_view.refreshFinish(PullToRefreshLayout.SUCCEED);


    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

        // 千万别忘了告诉控件加载完毕了哦！
        refresh_view.loadmoreFinish(PullToRefreshLayout.SUCCEED);


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
                JLog.i(base.data.size() + "");
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
                    }
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                JLog.i(isOnCallback + "");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                JLog.i(cex + "");
            }

            @Override
            public void onFinished() {

            }
        });
    }


    // 获取轮播的数据
    @Override
    public void onItemClick(int position) {
        JLog.i("点击了第" + position + "张轮播图");
        if (bannerModels != null) {
            if (bannerModels.size() > 0) {
                BannerModel model = bannerModels.get(position);
                JLog.i("获取到的数据" + model.PID);
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
                Type type = new TypeToken<JsonBase<ArrayList<CatModel>>>() {
                }.getType();
                final JsonBase<ArrayList<CatModel>> base = gson
                        .fromJson(result, type);
                JLog.i(base.data.size() + "");
                if (base.data != null) {
                    if (base.data.size() > 0) {
                        adapter = new QuickAdapter<CatModel>(getActivity(), R.layout.listview_item_car, base.data) {
                            @Override
                            protected void convert(BaseAdapterHelper helper, final CatModel item) {
                                final int position = helper.getPosition();
                                if (base.data.get(position).FType.equals("1")) {
                                    helper.setText(R.id.tv_re_men_or_you_hui, "热门");
                                } else {
                                    helper.setText(R.id.tv_re_men_or_you_hui, "优惠");
                                }
                                helper.setText(R.id.tv_car_name, item.FCarName)
                                        .setText(R.id.tv_car_price, "¥" + item.FDayMoeny + "/")
                                        .setText(R.id.tv_car_remark, item.FRemark)
                                        .setImageUrl(R.id.iv_car_picture, item.FImg);
                                helper.getView(R.id.rrl_item).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        JLog.i("汽车的id===========" + item.KCarID);

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
                JLog.i(isOnCallback + "");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                JLog.i(cex + "");
            }

            @Override
            public void onFinished() {

            }
        });
    }


}