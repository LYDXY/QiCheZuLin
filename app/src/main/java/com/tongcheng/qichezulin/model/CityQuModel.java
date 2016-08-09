package com.tongcheng.qichezulin.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 林尧 on 2016/7/26.
 * 区县
 * 城市和区一起返回
 */


public class CityQuModel implements Serializable {
    public String CityName;//城市名
    public String CityId;//城市id
    public ArrayList<QuModel> quModels;//多个区县
}
