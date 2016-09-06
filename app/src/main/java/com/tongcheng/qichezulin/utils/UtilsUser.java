package com.tongcheng.qichezulin.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import com.code19.library.DateUtils;
import com.google.gson.Gson;
import com.jiongbull.jlog.JLog;
import com.tongcheng.qichezulin.model.UserModel;

import java.util.Date;


/**
 * Created by 林尧 on 2016/7/23.
 */

public class UtilsUser {

    public static final String KEY_ACCOUNT_OBJECT = "account_object";//整个账号对象
    public static final String KEY_USER_ID = "user_id";
    public static final String FILE_NAME = "info";
    public static final String KEY_FIRST_OPEN_YES_OR_NO = "KEY_FIRST_OPEN_YES_OR_NO";
    public static final String PWD = "pwd";
    public static final String TOKEN = "token";
    public static final String TOKEN_TIME = "token_time";
    public static final String USER_PHOTO = "user_photo";
    public static final String USER_NAME = "user_name";
    public static final String USER_PHOEN = "user_phone";
    public static final String FBONDMOENY = "FBondMoeny";

    //保存整个用户的信息 ---------采用json 字符串的形式
    public static void saveUser(Context context, UserModel info) {
        Gson gson = new Gson();
        String json = gson.toJson(info);
        setSP(context, KEY_ACCOUNT_OBJECT, json);
    }

    public static void setSP(Context context, String key, Object object) {
        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        if ("String".equals(type)) {
            edit.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            edit.putInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            edit.putBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            edit.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            edit.putLong(key, (Long) object);
        }
        edit.commit();

    }

    public static Object getSp(Context context, String key, Object defaultObject) {
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if ("String".equals(type)) {
            return sp.getString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }

    public static void cleanAllSP(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    //获取用户的信息
    public static UserModel getUser(Context context) {
        Gson gson = new Gson();
        String json = (String) getSp(context, KEY_ACCOUNT_OBJECT, "");
        return gson.fromJson(json, UserModel.class);
    }

    //获取token
    public static String getToken(Context context) {
        if (UtilsUser.getSp(context, UtilsUser.TOKEN, "") != null) {
            return UtilsUser.getSp(context, UtilsUser.TOKEN, "").toString();
        } else {
            Utils.ShowText(context, "token从缓存中拿不到");
            return "";
        }

    }

    // 判断token是否过期
    public static String get_is_chao_shi(Context context) {
        if (UtilsUser.getTOKEN_TIME(context).equals("")) {
            return "";
        } else {
            try{
                long jiange = DateUtils.subtractDate(DateUtils.string2Date(UtilsUser.getTOKEN_TIME(context), "yyyy-MM-dd HH:mm:ss"), new Date()) / 1000;//除以1000是为了转换成秒;
                if (jiange > 7200) {
                    Utils.ShowText(context,"token过期");
                    return "token过期";
                } else {
                    return UtilsUser.getToken(context);
                }
            }catch (Exception E){
                return "转换异常";
            }

        }

    }


    //获取用户id
    public static String getUserID(Context context) {
        if (UtilsUser.getSp(context, UtilsUser.KEY_USER_ID, "") != null) {
            return UtilsUser.getSp(context, UtilsUser.KEY_USER_ID, "").toString();
        } else {
            Utils.ShowText(context, "用户id从缓存中拿不到");
            return "";
        }

    }

    //获取TOKEN_TIME
    public static String getTOKEN_TIME(Context context) {
        if (UtilsUser.getSp(context, UtilsUser.TOKEN_TIME, "") != null) {

            return UtilsUser.getSp(context, UtilsUser.TOKEN_TIME, "").toString();
        } else {
            Utils.ShowText(context, "tokentime从缓存中拿不到");
            return "";
        }

    }

    //获取头像
    public static String getUSER_PHOTO(Context context) {
        if (UtilsUser.getSp(context, UtilsUser.USER_PHOTO, "") != null) {
            return UtilsUser.getSp(context, UtilsUser.USER_PHOTO, "").toString();
        } else {
            Utils.ShowText(context, "USER_PHOTO从缓存中拿不到");
            return "";
        }

    }

    //是否第一次
    public static String getKEY_FIRST_OPEN_YES_OR_NO(Context context) {
        if (UtilsUser.getSp(context, UtilsUser.KEY_FIRST_OPEN_YES_OR_NO, "") != null) {
            return UtilsUser.getSp(context, UtilsUser.KEY_FIRST_OPEN_YES_OR_NO, "").toString();
        } else {
            Utils.ShowText(context, "KEY_FIRST_OPEN_YES_OR_NO从缓存中拿不到");
            return "";
        }

    }

    //获取用户名
    public static String get_user_name(Context context) {
        if (UtilsUser.getSp(context, UtilsUser.USER_NAME, "") != null) {
            return UtilsUser.getSp(context, UtilsUser.USER_NAME, "").toString();
        } else {
            Utils.ShowText(context, "USER_NAME从缓存中拿不到");
            return "";
        }

    }

    //获取手机号码
    public static String get_user_phoen(Context context) {
        if (UtilsUser.getSp(context, UtilsUser.USER_PHOEN, "") != null) {
            return UtilsUser.getSp(context, UtilsUser.USER_PHOEN, "").toString();
        } else {
            Utils.ShowText(context, "USER_PHOEN 从缓存中拿不到");
            return "";
        }

    }
    //获取保证金
    public static String get_fbondmoeny(Context context) {
        if (UtilsUser.getSp(context, UtilsUser.FBONDMOENY, "") != null) {
            return UtilsUser.getSp(context, UtilsUser.FBONDMOENY, "").toString();
        } else {
            Utils.ShowText(context, "保证金 从缓存中拿不到");
            return "";
        }

    }

}
