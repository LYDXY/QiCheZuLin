package com.tongcheng.qichezulin.model;


import java.io.Serializable;

/**
 * Created by 林尧 on 2016/7/26.
 */


public class InvoicelistModel implements Serializable {
    public String PID;//抬头id
    public String KUserID; // 用户id
    public String FName;//抬头名称
    public String FState; //状态【1正常0作废】
}
