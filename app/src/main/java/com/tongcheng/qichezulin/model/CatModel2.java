package com.tongcheng.qichezulin.model;


import java.io.Serializable;

/**
 * Created by 林尧 on 2016/7/26.
 */


public class CatModel2 implements Serializable {


    public String KCarID;//
    public String FType; // 活动类型【1.热门2.优惠】
    public String FCarName;//汽车名称址
    public String FImg;//汽车图片
    public String FDayMoney; // 日均价格
    public String FRemark;//车辆描述
    public String PID;//汽车id
    public String KTypeID;//汽车分类ID
    public String FCreateDate;//创建时间
    public String FHourMoney;//时租金
    public String FMonthMoney;//月租金
    public String FBond;//保证金
    public String FPower;//动力
    public String FIsAutomatic;//手动/自动
    public String FDriveMethod;
    public String FSeat; //座位数量
    public String FIsNavigation; //是否有导航
    public String FIsCarWindows;//是否有天窗
    public String FState;//状态
}
