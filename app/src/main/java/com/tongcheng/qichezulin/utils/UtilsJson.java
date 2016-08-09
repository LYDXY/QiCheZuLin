package com.tongcheng.qichezulin.utils;

import com.jiongbull.jlog.JLog;


/**
 * Created by 林尧 on 2016/7/23.
 * 找控件的 方法
 */

public class UtilsJson {


    public static void printJsonData(String result) {
        JLog.i(result);
        JLog.json(result);
    }


}
