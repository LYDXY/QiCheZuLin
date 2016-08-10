package com.tongcheng.qichezulin.model;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 林尧 on 2016/7/26.
 * 区县
 * 城市和区一起返回
 */


public class QuShopsModel implements Serializable {

    public String CountyName;//区名字
    public String CountyId;//城市id
    public ArrayList<ShopModel> ShopModels;//多个门店

    public class ShopModel implements Serializable {
        public String ShopID;//店铺id
        public String FShopName;//店名
        public String FLongitude;//经度
        public String FLatitude;//纬度
        public String FAddress;//店铺地址
    }
}
