package com.tongcheng.qichezulin.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.tongcheng.qichezulin.model.UserModel;


/**
 * Created by 林尧 on 2016/7/23.
 */

public class UtilsUser {

    public static final String KEY_ACCOUNT_OBJECT = "account_object";//整个账号对象
    public static final String KEY_USER_ID = "user_id";
    public static final String FILE_NAME = "info";
    public static final String KEY_FIRST_OPEN_YES_OR_NO = "KEY_FIRST_OPEN_YES_OR_NO";

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
    public UserModel getUser(Context context) {
        Gson gson = new Gson();
        String json = (String) getSp(context, KEY_ACCOUNT_OBJECT, "");
        return gson.fromJson(json, UserModel.class);
    }
}
