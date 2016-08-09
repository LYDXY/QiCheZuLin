package com.tongcheng.qichezulin.utils;


import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.code19.library.VerificationUtils;
import com.jiongbull.jlog.JLog;

/**
 * Created by 林尧 on 2016/7/23.
 * 找控件的 方法
 */

public class Utils {


    private static Toast toast = null;

    //解决循环点击弹出的toast 问题
    private static void way(Context activity, String str) {
        if (toast == null) {
            toast = Toast.makeText(activity, str, Toast.LENGTH_LONG);
        } else {
            toast.setText(str);
            toast.setDuration(Toast.LENGTH_LONG);
        }
        toast.show();
    }

    /**
     * 替代findviewById方法
     */
    public static <T extends View> T find(View view, int id) {
        return (T) view.findViewById(id);
    }

    // 只有填入的是手机号码才为 true
    public static boolean isPhoneNumber(EditText et_phone_number, Context activity) {
        String str = et_phone_number.getText().toString().trim();
        if (str == "" || str.equals("")) {
            way(activity, "请输入手机号码");
            return false;
        } else if (VerificationUtils.matcherPhoneNum(str) == false) {
            way(activity, "手机号码输入不正确");
            return false;
        } else {
            JLog.i("手机号码输入正确");
            return true;
        }

    }

    //判断字符串是否为空
    public static boolean yanZhengMaIsEmpty(EditText editText, Context activity, String showText) {
        String str = editText.getText().toString().trim();
        if (str == "" || str.equals("")) {
            way(activity, showText);
            return false;
        } else {
            return true;
        }
    }


    //简单的弹出提示
    public static void ShowText(Context activity, String showText) {
        if (toast == null) {
            toast = Toast.makeText(activity, showText, Toast.LENGTH_LONG);
        } else {
            toast.setText(showText);
            toast.setDuration(Toast.LENGTH_LONG);
        }
        toast.show();
    }
}
