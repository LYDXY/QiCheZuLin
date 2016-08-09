package com.tongcheng.qichezulin.utils;


/**
 * Created by 林尧 on 2016/7/23.
 * 找控件的 方法
 */

public class UtilsMath {


    public static String getjuli(int x) {
        return (Math.round(x / 100d)) / 10d + "千米";
    }

}
