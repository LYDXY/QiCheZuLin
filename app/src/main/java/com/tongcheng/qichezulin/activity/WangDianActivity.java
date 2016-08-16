package com.tongcheng.qichezulin.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiongbull.jlog.JLog;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.tongcheng.qichezulin.Param.ParamNearWangDian;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.listner.MyLocationListener;
import com.tongcheng.qichezulin.model.BannerShop;
import com.tongcheng.qichezulin.model.JsonBase;
import com.tongcheng.qichezulin.utils.UtilsJson;
import com.tongcheng.qichezulin.utils.UtilsMath;
import com.tongcheng.qichezulin.utils.UtilsTiaoZhuang;

import org.xutils.common.Callback;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 林尧 on 2016/8/15.
 */
public class WangDianActivity extends Activity implements View.OnClickListener {
    BitmapDescriptor bitmap = BitmapDescriptorFactory
            .fromResource(R.mipmap.home2);
    boolean isfirst = true;
    ImageView iv_meng_dian;//门店
    ImageView iv_chongdianzhan;
    ImageView iv_ting_ch_che_chang;
    ImageView iv_mei_rong_rong_yuan;
    ImageView iv_return;
    TextView tv_first;
    TextView tv_second;
    LocationClient locationClient;
    private List<String> shopid = new ArrayList<String>();
    private List<String> shopname = new ArrayList<String>();
    private List<LatLng> latLngs = new ArrayList<LatLng>();
    private List<BannerShop> shops = null;
    private LatLng point;
    private MapView mapView;
    private BaiduMap baiduMap;
    public BDLocationListener bdLocationListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            point = new LatLng(bdLocation.getLatitude(),
                    bdLocation.getLongitude());
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(point, 14);   //设置地图中心点以及缩放级别
//              MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(u);
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude()).build();
            baiduMap.setMyLocationData(locData);    //设置定位数据
            get_meng_dian_data();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wang_dian_map);
        mapView = (MapView) findViewById(R.id.bmapView);
        tv_first = (TextView) findViewById(R.id.tv_first);
        tv_second = (TextView) findViewById(R.id.tv_second);
        iv_return = (ImageView) findViewById(R.id.iv_return);
        iv_meng_dian = (ImageView) findViewById(R.id.iv_meng_dian);
        iv_chongdianzhan = (ImageView) findViewById(R.id.iv_chongdianzhan);
        iv_ting_ch_che_chang = (ImageView) findViewById(R.id.iv_ting_ch_che_chang);
        iv_mei_rong_rong_yuan = (ImageView) findViewById(R.id.iv_mei_rong_rong_yuan);
        iv_meng_dian.setOnClickListener(this);
        iv_chongdianzhan.setOnClickListener(this);
        iv_ting_ch_che_chang.setOnClickListener(this);
        iv_mei_rong_rong_yuan.setOnClickListener(this);
        iv_return.setOnClickListener(this);
        tv_second.setOnClickListener(this);
        tv_first.setText("附近服务点");
        tv_second.setText("列表");
        tv_first.setVisibility(View.VISIBLE);
        tv_second.setVisibility(View.VISIBLE);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true); //激活定位图层
        baiduMap.setBuildingsEnabled(true);
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
            case R.id.iv_meng_dian:
                JLog.w(latLngs.size() + "");
                if (latLngs.size() > 0) {
                    setMarker(latLngs, shopid, shopname);
                }

                break;
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

    //获取门店数据
    public void get_meng_dian_data() {
        ParamNearWangDian nearWangDian = new ParamNearWangDian();
        nearWangDian.lng = point.longitude + "";
        nearWangDian.lat = point.latitude + "";
        nearWangDian.type_id = "1";
        Callback.Cancelable cancelable
                = x.http().post(nearWangDian, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                UtilsJson.printJsonData(result);
                Gson gson = new Gson();
                Type type = new TypeToken<JsonBase<ArrayList<BannerShop>>>() {
                }.getType();
                JsonBase<ArrayList<BannerShop>> base = gson
                        .fromJson(result, type);
                if (!base.status.toString().trim().equals("0")) {
                    if (base.data.size() > 0) {
                        for (int i = 0; i < base.data.size(); i++) {
                            latLngs.add(new LatLng(Double.parseDouble(base.data.get(i).FLatitude), Double.parseDouble(base.data.get(i).FLongitude)));
                            shopid.add(base.data.get(i).PID);
                            shopname.add(base.data.get(i).FShopName);
                        }
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


    public void setMarker(List<LatLng> points, final List<String> shopid, List<String> shopname) {


        //效果

        for (int i = 0; i < points.size(); i++) {
            final String ID = shopid.get(i);
            final String name = shopname.get(i);
            MarkerOptions ooD = new MarkerOptions().position(points.get(i))
                    .icon(bitmap).zIndex(10).period(1).alpha(0.8f).rotate(0.5f).flat(false);

            if (isfirst) {
                ooD.animateType(MarkerOptions.MarkerAnimateType.drop);
                isfirst = false;

            }
            baiduMap.addOverlay(ooD);
            Button button = new Button(getApplicationContext());
            button.setBackgroundResource(R.mipmap.bg0000);
            button.setGravity(0x01);
            button.setText(name);
            button.setTextColor(getResources().getColor(R.color.whiteFFFFFF));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JLog.w(ID);
                    UtilsTiaoZhuang.ToAnotherActivity(WangDianActivity.this, WangDianDetailActivity.class);
                }
            });
            final InfoWindow mInfoWindow = new InfoWindow(button, points.get(i), -100);
            //标注物点击
            baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    baiduMap.showInfoWindow(mInfoWindow);
                    return true;
                }
            });

        }


    }
}
