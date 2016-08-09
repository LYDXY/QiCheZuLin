package com.tongcheng.qichezulin.model;


import java.io.Serializable;

/**
 * Created by 林尧 on 2016/7/26.
 */


public class CatModel implements Serializable {
    public String KCarID;//汽车ID
    public String FType; // 活动类型【1.热门2.优惠】
    public String FCarName;//汽车名称址
    public String FImg;//汽车图片
    public String FDayMoeny; // 日均价格
    public String FRemark;//车辆描述

}
