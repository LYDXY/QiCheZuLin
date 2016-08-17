package com.tongcheng.qichezulin.utils;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tongcheng.qichezulin.model.BannerShop;
import com.tongcheng.qichezulin.model.CarModel;
import com.tongcheng.qichezulin.model.CarModel2;


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


    public static Bundle EveryCar(CarModel2 item) {
        Bundle bundle = new Bundle();
        bundle.putString("FImg", item.FImg);
        bundle.putString("FCarName", item.FCarName);
        bundle.putString("FDayMoney", item.FDayMoney);
        bundle.putString("FType", item.FType);
        bundle.putString("KCarID", item.KCarID);
        bundle.putString("FRemark", item.FRemark);
        bundle.putString("PID", item.PID);
        bundle.putString("KTypeID", item.KTypeID);
        bundle.putString("FCreateDate", item.FCreateDate);
        bundle.putString("FHourMoney", item.FHourMoney);
        bundle.putString("FMonthMoney", item.FMonthMoney);
        bundle.putString("FPower", item.FPower);
        bundle.putString("FIsAutomatic", item.FIsAutomatic);
        bundle.putString("FDriveMethod", item.FDriveMethod);
        bundle.putString("FIsNavigation", item.FIsNavigation);
        bundle.putString("FIsCarWindows", item.FIsCarWindows);
        bundle.putString("FState", item.FState);
        bundle.putString("FBond", item.FBond);
        bundle.putString("FSeat", item.FSeat);
        return bundle;
    }


    public static Bundle EveryCar(CarModel item) {
        Bundle bundle = new Bundle();
        bundle.putString("FImg", item.FImg);
        bundle.putString("FCarName", item.FCarName);
        bundle.putString("FDayMoney", item.FDayMoney);
        bundle.putString("FType", item.FType);
        bundle.putString("KCarID", item.KCarID);
        bundle.putString("FRemark", item.FRemark);
        bundle.putString("PID", item.PID);
        bundle.putString("KTypeID", item.KTypeID);
        bundle.putString("FCreateDate", item.FCreateDate);
        bundle.putString("FHourMoney", item.FHourMoney);
        bundle.putString("FMonthMoney", item.FMonthMoney);
        bundle.putString("FPower", item.FPower);
        bundle.putString("FIsAutomatic", item.FIsAutomatic);
        bundle.putString("FDriveMethod", item.FDriveMethod);
        bundle.putString("FIsNavigation", item.FIsNavigation);
        bundle.putString("FIsCarWindows", item.FIsCarWindows);
        bundle.putString("FState", item.FState);
        bundle.putString("FBond", item.FBond);
        bundle.putString("FSeat", item.FSeat);
        return bundle;
    }


    public static Bundle get_BannerShop(BannerShop item) {
        Bundle bundle = new Bundle();
        bundle.putString("FImg", item.FImg);
        bundle.putString("FAddress", item.FAddress);
        bundle.putString("FShopName", item.FShopName);
        bundle.putString("FType", item.FType);
        bundle.putString("FCreateDate", item.FCreateDate);
        bundle.putString("FImg1", item.FImg1);
        bundle.putString("PID", item.PID);
        bundle.putString("FImg2", item.FImg2);
        bundle.putString("FImg3", item.FImg3);
        bundle.putString("FIsBusiness", item.FIsBusiness);
        bundle.putString("FKFTel", item.FKFTel);
        bundle.putString("FLatitude", item.FLatitude);
        bundle.putString("FLongitude", item.FLongitude);
        bundle.putString("FState", item.FState);
        bundle.putString("FTel", item.FTel);
        bundle.putString("FTime", item.FTime);
        bundle.putString("KCityID", item.KCityID);
        return bundle;
    }


    public static void ToAnotherActivity(Context activity, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(activity, cls);
        activity.startActivity(intent);
    }
}
