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
     * 获取科室导航的数据URL
     */
    public static final String GET_DEPARTMENT_NAV_URL=BASE_API_URL+"/interface/patient/appointment/getdepart";
    /***
     * 我的咨询列表URL
     */
    public static final String GET_USER_COUNSEL_LIST_URL=BASE_API_URL+"/interface/patient/consultation/getlist";

    /**
     * 用户注册URL
     */
    public static final String REGISTER_URL=BASE_API_URL+"/interface/patient/user/register";
    /**
     * 我的订单列表URL
     */
    public static final String USER_ORDER_LIST=BASE_API_URL+"/interface/patient/myorder/getlist";
    /***
     * 我的病历列表URL
     */
    public static final String USER_MEDICAL_LIST_URL=BASE_API_URL+"/interface/patient/myrecord/getlist";

    /**
     * 我的医生列表URL
     */
    public static final String USER_DOCTOR_LIST_URL=BASE_API_URL+"/interface/patient/mydoctor/getlist";
    /***
     *  获取医生评价详情
     */
    public static final String GET_USER_DOCTOR_EVALUATION_URL =BASE_API_URL+"/interface/patient/doctorevaluation/getdetail";
    /***
     *  提交医生评价
     */
    public static final String ADD_USER_DOCTOR_EVALUATION_URL =BASE_API_URL+"/interface/patient/doctorevaluation/add";

    /**
     * 我的订单搜索URL
     */
    public static final String USER_ORDER_SEARCH=BASE_API_URL+"/interface/patient/myorder/search";

    /**
     * 在线售药首页URL
     */
    public static final String ONLINE_URL=BASE_API_URL+"/interface/patient/mall/getindex";
}
