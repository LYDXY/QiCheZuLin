package com.tongcheng.qichezulin.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.utils.UtilsTiaoZhuang;
import com.tongcheng.qichezulin.utils.UtilsUser;


import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by 林尧 on 2016/7/22.
 */

@ContentView(R.layout.activity_start)
public class StartActivity extends Activity {



    @ViewInject(R.id.bt_go)
    Button bt_go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        JLog.v("onCreate");

        //为空
        if (UtilsUser.getSp(getApplication(), UtilsUser.KEY_FIRST_OPEN_YES_OR_NO, "") == null
                || UtilsUser.getSp(getApplication(), UtilsUser.KEY_FIRST_OPEN_YES_OR_NO, "").equals("")) {
            JLog.w("第一次打开应用");
            UtilsUser.setSP(getApplication(), UtilsUser.KEY_FIRST_OPEN_YES_OR_NO, "00000000000");
            bt_go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UtilsTiaoZhuang.ToAnotherActivity(StartActivity.this, LoginActivity.class);
                }
            });
        }
        //不为空
        else {

            JLog.w("已经打开多次应用了");
            JLog.w(UtilsUser.getSp(getApplication(), UtilsUser.KEY_FIRST_OPEN_YES_OR_NO, "").toString() + "////////////////");
            //为空
            if (UtilsUser.getSp(getApplication(), UtilsUser.KEY_USER_ID, "") == null) {
                JLog.w("跳转到登录界面");
                UtilsTiaoZhuang.ToAnotherActivity(this, LoginActivity.class);
                this.finish();
            }
            //不为空
            else {
                JLog.w(UtilsUser.getSp(getApplication(), UtilsUser.KEY_USER_ID, "").toString() + "==================");
                JLog.w("跳转到主界面");
                UtilsTiaoZhuang.ToAnotherActivity(this, MainActivity2.class);
                this.finish();

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JLog.w("onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        JLog.w("onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        JLog.w("onPause");
    }
}
