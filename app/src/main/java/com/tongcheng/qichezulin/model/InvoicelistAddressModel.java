package com.tongcheng.qichezulin.model;


import java.io.Serializable;

/**
 * Created by 林尧 on 2016/7/26.
 */


public class InvoicelistAddressModel implements Serializable {
    public String PID;//抬头地址id
    public String KUserID; // 用户id
    public String FName;//姓名
    public String FMobile;//手机号码
    public String FProv;//省份
    public String FCity;//城市
    public String FDist;//区县
    public String FInvoiceAddress;//地址
    public String FPostCode;//邮政编码
    public String FState; //状态【1正常0作废】
    public String SName;//省
    public String CName;//市
    public String DName;//区
}
