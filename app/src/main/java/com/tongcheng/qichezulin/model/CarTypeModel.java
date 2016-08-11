package com.tongcheng.qichezulin.model;


import java.io.Serializable;

/**
 * Created by 林尧 on 2016/7/26.
 */


public class CarTypeModel implements Serializable {
    public String PID;//类型ID
    public String FTypeName;//类型名称
    public String FOperate;//运营性质[1.货运车2.乘用车]
    public String FCreateDate;//创建时间
    public String FState;//状态【1正常0作废】
}
