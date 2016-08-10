package com.tongcheng.qichezulin.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tongcheng.qichezulin.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by 林尧 on 2016/8/10.
 */
public abstract class PuTongActivity2 extends Activity implements View.OnClickListener {

    @ViewInject(R.id.iv_return)
    ImageView iv_return;
    @ViewInject(R.id.tv_first)
    TextView tv_first;

    abstract void initData();

    abstract void initView();

    void setListenerOnView() {
        iv_return.setOnClickListener(this);
        tv_first.setOnClickListener(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

}