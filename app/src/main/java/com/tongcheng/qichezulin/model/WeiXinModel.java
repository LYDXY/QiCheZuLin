package com.tongcheng.qichezulin.model;


import java.io.Serializable;

/**
 * Created by 林尧 on 2016/7/26.
 */


public class WeiXinModel implements Serializable {
    public String appid;//微信appid
    public String noncestr;//
    public String pkg;
    public String partnerid;
    public String prepayid;
    public String timestamp;
    public String sign;
}
