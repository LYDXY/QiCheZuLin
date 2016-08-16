package com.tongcheng.qichezulin.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.listner.MyLocationListener;
import com.tongcheng.qichezulin.utils.UtilsTiaoZhuang;

/**
 * Created by 林尧 on 2016/8/15.
 */
public class WangDianActivity extends Activity implements View.OnClickListener {


    ImageView iv_return;
    TextView tv_first;
    TextView tv_second;
    LocationClient locationClient;
    private MapView mapView;
    private BaiduMap baiduMap;
    public BDLocationListener bdLocationListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            LatLng ll = new LatLng(bdLocation.getLatitude(),
                    bdLocation.getLongitude());
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, 16);   //设置地图中心点以及缩放级别
//              MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(u);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_wang_dian_map);
        mapView = (MapView) findViewById(R.id.bmapView);
        tv_first = (TextView) findViewById(R.id.tv_first);
        tv_second = (TextView) findViewById(R.id.tv_second);
        iv_return = (ImageView) findViewById(R.id.iv_return);
        iv_return.setOnClickListener(this);
        tv_second.setOnClickListener(this);
        tv_first.setText("附近服务点");
        tv_second.setText("列表");
        tv_first.setVisibility(View.VISIBLE);
        tv_second.setVisibility(View.VISIBLE);


        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true); //激活定位图层
        locationClient = new LocationClient(getApplicationContext());
        this.setLocationOption();   //设置定位参数
        locationClient.start(); // 开始定位

    }


    /**
     * 设置定位参数
     */
    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开GPS
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
        option.setCoorType("bd09ll"); // 返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(0); // 设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true); // 返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true); // 返回的定位结果包含手机机头的方向
        locationClient.setLocOption(option);
        locationClient = new LocationClient(getApplicationContext()); // 实例化LocationClient类
        locationClient.registerLocationListener(bdLocationListener); // 注册监听函数

        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL); // 设置为一般地图
        // baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE); //设置为卫星地图
        // baiduMap.setTrafficEnabled(true); //开启交通图
    }


    // 三个状态实现地图生命周期管理
    @Override
    protected void onDestroy() {
        //退出时销毁定位
        locationClient.stop();
        baiduMap.setMyLocationEnabled(false);
        super.onDestroy();
        mapView.onDestroy();
        mapView = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {

        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_return:
                onBackPressed();
                break;
            case R.id.tv_second:
                JLog.w("网点查询");
                if (MyLocationListener.latitude != null && MyLocationListener.lontitude != null) {
                    JLog.w("经度" + MyLocationListener.latitude);
                    JLog.w("纬度" + MyLocationListener.lontitude);
                    Bundle bundle = new Bundle();
                    bundle.putString("latitude", MyLocationListener.latitude);
                    bundle.putString("lontitude", MyLocationListener.lontitude);
                    UtilsTiaoZhuang.ToAnotherActivity(WangDianActivity.this, WangDianSearchActivity.class, bundle);
                } else {
                    JLog.w("定位失败");
                }
                break;
        }
    }
}
