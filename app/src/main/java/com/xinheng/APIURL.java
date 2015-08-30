package com.xinheng;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/20 0020
 * 时间： 15:17
 * 说明： 对访问的url进行统一管理
 */
public class APIURL
{

    public static final String BASE_API_URL="http://139.196.24.205:8080";
//    public static final String BASE_API_URL=" http://192.168.2.119";
    /**
     * 用户登录URL
     */
    public static final String LOGIN_URL=BASE_API_URL+"/interface/patient/user/login";
    /**
     * 用户注册URL
     */
    public static final String REGISTER_URL=BASE_API_URL+"/interface/patient/user/register";
    /**
     * 我的订单列表URL
     */
    public static final String USER_ORDER_LIST=BASE_API_URL+"/interface/patient/myorder/getlist";

    /**
     * 我的订单搜索URL
     */
    public static final String USER_ORDER_SEARCH=BASE_API_URL+"/interface/patient/myorder/search";

    /**
     * 在线售药首页URL
     */
    public static final String ONLINE_URL=BASE_API_URL+"/interface/patient/mall/getindex";
}
