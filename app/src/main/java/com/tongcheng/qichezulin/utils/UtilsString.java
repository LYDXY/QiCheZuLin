package com.tongcheng.qichezulin.utils;


import com.code19.library.StringUtils;

import java.text.DecimalFormat;

/**
 * Created by 林尧 on 2016/7/23.
 * 找控件的 方法
 */

public class UtilsString {

    //将手机号码中间的4个字符串隐藏掉

    public static String hidePhoneNumber(String PhoneNumber) {
        return PhoneNumber.substring(0, 3) + "****" + PhoneNumber.substring(7, 11);
    }

    //保留小数点 0.00 #.##
    public static String strToFloat2(Float zhi,String format){
        DecimalFormat decimalFormat = new DecimalFormat(format);
        return decimalFormat.format(zhi);
    }



}
