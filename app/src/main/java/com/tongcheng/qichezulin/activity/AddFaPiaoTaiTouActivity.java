package com.tongcheng.qichezulin.activity;

import android.os.Bundle;
import android.view.View;

import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.R;

import org.xutils.view.annotation.ContentView;

/**
 * Created by 林尧 on 2016/8/18.
 */

@ContentView(R.layout.activity_add_fa_piao_tai_tou)
public class AddFaPiaoTaiTouActivity extends PuTongActivity {
    @Override
    void initData() {

    }

    @Override
    void initView() {
        tv_first.setVisibility(View.VISIBLE);
        tv_first.setText("新增发票抬头");
        tv_second.setText("保存");
        tv_second.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                onBackPressed();
                break;
            case R.id.tv_second:
                JLog.i("保存");
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
        setListenerOnView();
    }
}
