package com.tongcheng.qichezulin.utils;


/**
 * Created by 林尧 on 2016/7/23.
 * 找控件的 方法
 */

public class UtilsString {

    //将手机号码中间的4个字符串隐藏掉

    public static String hidePhoneNumber(String PhoneNumber) {
        return PhoneNumber.substring(0, 3) + "****" + PhoneNumber.substring(7, 11);
    }


}
