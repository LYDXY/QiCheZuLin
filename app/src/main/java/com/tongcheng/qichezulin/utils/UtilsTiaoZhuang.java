package com.tongcheng.qichezulin.utils;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


/**
 * Created by 林尧 on 2016/7/23.
 * 找控件的 方法
 */

public class UtilsTiaoZhuang {


    public static void ToAnotherActivity(Context activity, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        activity.startActivity(intent);
    }

    public static void ToAnotherActivity(Context activity, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(activity, cls);
        activity.startActivity(intent);
    }
}
