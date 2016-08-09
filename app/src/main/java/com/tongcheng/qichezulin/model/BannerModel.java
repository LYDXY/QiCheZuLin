package com.tongcheng.qichezulin.model;


import java.io.Serializable;

/**
 * Created by 林尧 on 2016/7/26.
 */


public class BannerModel implements Serializable {
    public String PID;//广告ID
    public String FType; // 预留字段
    public String FImg;//图片地址
    public String FUrl;//跳转链接
    public String FSort; // 显示顺序
    public String KShopID;//跳转网点 id
    public String FState; //状态【1正常0作废】
}
