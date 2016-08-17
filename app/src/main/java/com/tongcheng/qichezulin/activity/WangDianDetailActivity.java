package com.tongcheng.qichezulin.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.utils.UtilsTiaoZhuang;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by 林尧 on 2016/8/10.
 */

@ContentView(R.layout.activity_wand_dian_detail)
public class WangDianDetailActivity extends PuTongActivity0 {


    @ViewInject(R.id.tv_show_shop_name)
    TextView tv_show_shop_name;
    @ViewInject(R.id.tv_time)
    TextView tv_time;

    @ViewInject(R.id.iv_left_wang_dian_picture)
    ImageView iv_left_wang_dian_picture;

    @ViewInject(R.id.iv_zhong_wang_dian_picture)
    ImageView iv_zhong_wang_dian_picture;

    @ViewInject(R.id.iv_right_wang_dian_picture)
    ImageView iv_right_wang_dian_picture;

    @ViewInject(R.id.tv_address)
    TextView tv_address;

    @ViewInject(R.id.tv_phone_number)
    TextView tv_phone_number;

    @ViewInject(R.id.iv_yu_yue_che_liang)
    ImageView iv_yu_yue_che_liang;

    @Override
    void initData() {


    }

    @Override
    void initView() {
        tv_first.setVisibility(View.VISIBLE);
        tv_first.setText("网点详情");
        iv_set_up.setVisibility(View.VISIBLE);
        iv_set_up.setBackgroundResource(R.mipmap.share);

        JLog.w(getIntent().getExtras().get("FImg") + "");
        try {
            if (getIntent().getExtras().get("FAddress") != null) {
                tv_address.setText(getIntent().getExtras().get("FAddress") + "");
            }
            if (getIntent().getExtras().get("FShopName") != null) {
                tv_show_shop_name.setText(getIntent().getExtras().get("FShopName") + "");
            }
            if (getIntent().getExtras().get("FImg") != null) {
                x.image().bind(iv_left_wang_dian_picture, getIntent().getExtras().get("FImg") + "");
            }
            if (getIntent().getExtras().get("FImg2") != null) {
                x.image().bind(iv_zhong_wang_dian_picture, getIntent().getExtras().get("FImg2") + "");
            }
            if (getIntent().getExtras().get("FImg3") != null) {
                x.image().bind(iv_right_wang_dian_picture, getIntent().getExtras().get("FImg3") + "");
            }
            if (getIntent().getExtras().get("FTel") != null) {
                tv_phone_number.setText(getIntent().getExtras().get("FTel") + "");
            }
            if (getIntent().getExtras().get("FTime") != null) {
                tv_time.setText(getIntent().getExtras().get("FTime") + "");
            }


        } catch (Exception E) {
            E.printStackTrace();
        }


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_return:
                onBackPressed();
                break;
            case R.id.iv_set_up:
                JLog.w("分享");
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListenerOnView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        JLog.w("onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JLog.w("onDestroy");
    }
}
