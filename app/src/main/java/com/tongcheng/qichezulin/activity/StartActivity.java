package com.tongcheng.qichezulin.activity;

import android.app.Activity;
import android.os.Bundle;


import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.utils.UtilsTiaoZhuang;
import com.tongcheng.qichezulin.utils.UtilsUser;


import org.xutils.view.annotation.ContentView;

/**
 * Created by 林尧 on 2016/7/22.
 */

@ContentView(R.layout.activity_start)
public class StartActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JLog.v("onCreate");
        //为空
        if (UtilsUser.getSp(getApplication(), UtilsUser.KEY_FIRST_OPEN_YES_OR_NO, "") == null
                || UtilsUser.getSp(getApplication(), UtilsUser.KEY_FIRST_OPEN_YES_OR_NO, "").equals("")) {
            JLog.i("第一次打开应用");
            UtilsUser.setSP(getApplication(), UtilsUser.KEY_FIRST_OPEN_YES_OR_NO, "00000000000");
        }
        //不为空
        else {
            JLog.i("已经打开多次应用了");
            JLog.i(UtilsUser.getSp(getApplication(), UtilsUser.KEY_FIRST_OPEN_YES_OR_NO, "").toString() + "////////////////");
            //为空
            if (UtilsUser.getSp(getApplication(), UtilsUser.KEY_USER_ID, "") == null || UtilsUser.getSp(getApplication(), UtilsUser.KEY_USER_ID, "").equals("")) {
                JLog.i("跳转到登录界面");
                UtilsTiaoZhuang.ToAnotherActivity(this, loginActivity.class);
                this.finish();
            }
            //不为空
            else {
                JLog.i(UtilsUser.getSp(getApplication(), UtilsUser.KEY_USER_ID, "").toString() + "==================");
                JLog.i("跳转到主界面");
                UtilsTiaoZhuang.ToAnotherActivity(this, MainActivity2.class);
                this.finish();
            }
        }

        //  UtilsUser.cleanAllSP(getApplication());
    }


}
