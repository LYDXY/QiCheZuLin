package com.tongcheng.qichezulin.model;


import java.io.Serializable;

/**
 * Created by 林尧 on 2016/7/26.
 */


public class UserModel implements Serializable {
    public String PID; //用户ID
    public String FUserName;//用户昵称
    public String FImg; //头像
    public String FPassWord;//密码
    public String FMobilePhone;//手机
    public String FSex;//性别【1男0女】
    public String FMoeny;//钱包金额
    public String FPayWord;//支付密码
    public String FBondMoeny;//保证金
    public String FJiFen;//积分
    public String FAndroidCode;//安卓唯一标识
    public String FIOSCode;//苹果唯一标识
    public String FWXCode;//微信唯一标识
    public String FCreateDate;//创建日期
    public String FState;//状态【1正常0作废】
}
