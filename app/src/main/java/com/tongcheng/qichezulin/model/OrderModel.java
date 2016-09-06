package com.tongcheng.qichezulin.model;


import java.io.Serializable;

/**
 * Created by 林尧 on 2016/7/26.
 */


public class OrderModel implements Serializable {
  public String  PID;//订单ID
  public String  FStartTime;//租车开始时间
  public String  FEndTime;//租车结束时间
  public String  FShopName;//租车门店
  public String  FCarName;//车辆名称
  public String  FImg;//图片
  public String  FCreateDate;//下单时间
  public String  TotalAmount;//订单总金额
  public String  FIsBack;//申请归还【1.申请2.归还】
  public String  FCode;//订单号
}
