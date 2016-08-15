package com.tongcheng.qichezulin.activity;

import android.app.Activity;
import android.os.Bundle;

import com.baidu.mapapi.map.MapView;
import com.tongcheng.qichezulin.R;

/**
 * Created by 林尧 on 2016/8/15.
 */
public class ActivityAAA extends Activity {
    private MapView mMapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_wang_dian_map);
        mMapView = (MapView) findViewById(R.id.bmapView);

    }

}
