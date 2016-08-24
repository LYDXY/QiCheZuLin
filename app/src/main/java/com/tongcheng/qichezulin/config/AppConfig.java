package com.tongcheng.qichezulin.config;

/**
 * Created by 林尧 on 2016/7/26.
 */
public class AppConfig {

    //域名 或者ip 地址
    public static final String HOST = "http://120.24.236.80:8090";

    //   public static final String HOST = "http://192.168.0.123:8090";

    //广告轮播地址
    public static final String GET_BANNER = "api/advert/get_advert.ashx";

    //广告轮播地址
    public static final String GET_REGIST = "api/user/register_user.ashx";


    //发送验证码
    public static final String GET_CHECK_CODE = "api/message/send_yzm.ashx";

    //密码登录
    public static final String GET_USER_LOGIN = "api/user/user_login.ashx";

    //热门车型列表
    public static final String GET_RE_MEN_CHE_XING = "api/car/get_hotcar.ashx";

    //验证码登录
    public static final String GET_USER_LOGIN_BY_CHECK_CODE = "api/user/user_loginbycode.ashx";

    //获取个人信息
    public static final String GET_USER_INFO = "api/user/get_userinfo.ashx";


    //二级目录
    public static final String GET_CITY_QU = "api/car/get_citycounty.ashx";

    //网点列表 根据地区id
    public static final String GET_WANG_DIAN_LIST = "api/shop/get_shoplist.ashx";


    //获取默认网点(离我最近)
    public static final String GET_DEFAULTSHOP = "api/shop/get_defaultshop.ashx";

    //获取网点列表 根据城市id
    public static final String GET_WANG_DIAN_LIST_BY_CITYID = "api/car/get_countyshop.ashx";

    //获取车的类型
    public static final String GET_CAR_TYPE = "api/car/get_cartype.ashx";

    //获取全部车型列表
    public static final String GET_ALL_CAR_LIST = "api/car/get_allcarlist.ashx";

    //获取附近网点列表
    public static final String GET_GET_NEARBYSHOP_WANG_DIAN_LIST = "api/shop/get_nearbyshop.ashx";


    //获取订单列表
    public static final String GET_GET_ORDERLIST = "api/order/get_orderlist.ashx";

    //删除订单
    public static final String UPDATE_ORDERDELETE = "api/order/update_orderdelete.ashx";


    //获取门店车型列表
    public static final String GET_CARLIST = "api/car/get_carlist.ashx";

    //获取收费服务项目
    public static final String GET_EXPENSE = "api/car/get_expense.ashx";

    //获取预付款比例
    public static final String GET_PROPORTION = "api/system/get_proportion.ashx";

    //获取用户积分
    public static final String GET_USERJF = "api/user/get_userjf.ashx";

    //插入订单
    public static final String SET_ORDER = "api/order/set_order.ashx";

}
