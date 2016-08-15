package com.tongcheng.qichezulin.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.LogoPosition;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.model.inner.Point;
import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.config.RootApp;
import com.tongcheng.qichezulin.listner.MyLocationListener;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by 林尧 on 2016/7/22.
 */

@ContentView(R.layout.fragment_wang_dian_map)
public class WangDianFragment extends PuTongFragment {


    // 定位相关声明
    public LocationClient locationClient = null;
    @ViewInject(R.id.bmapView)
    MapView mapView;
    BaiduMap baiduMap;
    public BDLocationListener myListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            baiduMap.setMyLocationData(locData);    //设置定位数据

            LatLng ll = new LatLng(location.getLatitude(),
                    location.getLongitude());
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, 16);   //设置地图中心点以及缩放级别
//              MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(u);

        }
    };
    //自定义图标
    BitmapDescriptor mCurrentMarker = null;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setClickListenerOnView();
        SDKInitializer.initialize(getContext());
        mapView.showScaleControl(false);
        mapView.showZoomControls(false);
        baiduMap = mapView.getMap();
        //开启定位图层
        baiduMap.setMyLocationEnabled(true);
        locationClient = new LocationClient(getActivity()); // 实例化LocationClient类
        locationClient.registerLocationListener(myListener); // 注册监听函数
        this.setLocationOption();   //设置定位参数
        locationClient.start(); // 开始定位
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL); // 设置为一般地图

    }

    @Override
    void setClickListenerOnView() {

    }

    @Override
    void initData() {

    }

    void initView() {
    }

    /* @Override
     void initView() {
         mMapView.showScaleControl(false);
         mMapView.showZoomControls(false);
         mBaiduMap = mMapView.getMap();
         mBaiduMap.setMyLocationEnabled(true);

         // mMapView.setLogoPosition(LogoPosition.logoPostionCenterTop);
         //定义Maker坐标点

         if (MyLocationListener.latitude == null || MyLocationListener.lontitude==null) {

         }else{
         final LatLng point = new LatLng(Double.parseDouble(MyLocationListener.latitude), Double.parseDouble(MyLocationListener.lontitude));
             mBaiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
                 @Override
                 public void onMapLoaded() {
                     mBaiduMap.setMapStatusLimits(new LatLngBounds.Builder().include(point).include(point).build());
                 }
             });

             mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL); //设置普通的地图类型
             JLog.i(mBaiduMap.getMaxZoomLevel() + "---------------"); //获取地图最大缩放级别
             mBaiduMap.setMaxAndMinZoomLevel(20, 15);
             //构建Marker图标
             BitmapDescriptor bitmap = BitmapDescriptorFactory
                     .fromResource(R.mipmap.hong2);
             //构建MarkerOption，用于在地图上添加Marker
             OverlayOptions option = new MarkerOptions()
                     .position(point)
                     .icon(bitmap);
             //在地图上添加Marker，并显示
             mBaiduMap.addOverlay(option);
         }


     }
 */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_get_yan_zheng_ma:

                break;
            case R.id.iv_qu_xiao:

                break;
            case R.id.btn_login:

                break;
        }
    }


    /**
     * 设置定位参数
     */
    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开GPS
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
        option.setCoorType("bd09ll"); // 返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(5000); // 设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true); // 返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true); // 返回的定位结果包含手机机头的方向
        locationClient.setLocOption(option);
    }

}
