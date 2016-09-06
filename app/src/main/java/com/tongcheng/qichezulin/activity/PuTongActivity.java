package com.tongcheng.qichezulin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by 林尧 on 2016/7/22.
 */
public abstract class PuTongActivity extends Activity implements View.OnClickListener {

    @ViewInject(R.id.iv_return)
    ImageView iv_return;
    @ViewInject(R.id.tv_first)
    TextView tv_first;
    @ViewInject(R.id.tv_second)
    TextView tv_second;

    abstract void initData();

    abstract void initView();

    void setListenerOnView() {
        iv_return.setOnClickListener(this);
        tv_first.setOnClickListener(this);
        tv_second.setOnClickListener(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

}
