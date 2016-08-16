package com.tongcheng.qichezulin.fragment;

import android.os.Bundle;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.listner.MyLocationListener;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by 林尧 on 2016/7/22.
 */

@ContentView(R.layout.activity_wang_dian_map)
public class WangDianFragment extends PuTongFragment {

    @ViewInject(R.id.bmapView)
    MapView mapView;
    BaiduMap baiduMap;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }

    @Override
    void setClickListenerOnView() {

    }

    @Override
    void initData() {

    }

     @Override
     void initView() {
         mapView.showScaleControl(false);
         mapView.showZoomControls(false);
         baiduMap = mapView.getMap();
         baiduMap.setMyLocationEnabled(true);
         if (MyLocationListener.latitude == null || MyLocationListener.lontitude==null) {

         }else{
         final LatLng point = new LatLng(Double.parseDouble(MyLocationListener.latitude), Double.parseDouble(MyLocationListener.lontitude));
             baiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
                 @Override
                 public void onMapLoaded() {
                     baiduMap.setMapStatusLimits(new LatLngBounds.Builder().include(point).include(point).build());
                 }
             });

             baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL); //设置普通的地图类型
             JLog.w(baiduMap.getMaxZoomLevel() + "---------------"); //获取地图最大缩放级别
             baiduMap.setMaxAndMinZoomLevel(20, 15);
             //构建Marker图标
             BitmapDescriptor bitmap = BitmapDescriptorFactory
                     .fromResource(R.mipmap.hong2);
             //构建MarkerOption，用于在地图上添加Marker
             OverlayOptions option = new MarkerOptions()
                     .position(point)
                     .icon(bitmap);
             //在地图上添加Marker，并显示
             baiduMap.addOverlay(option);
         }


     }

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
}
