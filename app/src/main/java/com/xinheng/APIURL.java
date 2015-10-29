package com.xinheng;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/20 0020
 * 时间： 15:17
 * 说明： 对访问的url进行统一管理
 */
public class APIURL
{
    public static final String BASE_API_URL = "http://139.196.24.205";
     // public static final String BASE_API_URL = " http://192.168.2.119";
    /**
     * 用户登录URL
     */
    public static final String LOGIN_URL = BASE_API_URL + "/public/patient/user/login";
    /***
     * 修改昵称URL
     */
    public static final String MODIFY_USER_NICKNAME_URL = BASE_API_URL + "/interface/patient/myaccount/editnickname";

    /**
     * 我的账户设置
     * 修改账户昵称URL
     */
    public static final String MODIFY_USER_ACCOUNT_NAME_URL = BASE_API_URL + "/interface/patient/myaccount/addaccount";
    /**
     * 获取我的账户详情URL
     */
    public static final String GET_USER_ACCOUNT_DETAIL_URL = BASE_API_URL + "/interface/patient/myaccount/getdetail";
    /***
     * 用户头像修改URL
     */
    public static final String USER_UPLOAD_PHOTO = BASE_API_URL + "/interface/patient/user/uploadphoto";
    /**
     * 获取科室导航的数据URL
     */
    public static final String GET_DEPARTMENT_NAV_URL = BASE_API_URL + "/interface/patient/appointment/getdepart";
    /**
     * 智能导诊-获取身体部位列表URL
     */

    public static final String GET_BODY_PART_LIST_URL = BASE_API_URL + "/interface/patient/selfcheck/getbodypart";
    /***
     * 智能导诊-获取症状列表URL
     */
    public static final String GET_BODY_PART_DETAIL_LIST_URL = BASE_API_URL + "/interface/patient/selfcheck/getsymptom";
    /***
     * 获取科室医生列表URL
     */
    public static final String GET_DEPARTMENT_DOCTOR_LIST_URL = BASE_API_URL + "/interface/patient/appointment/getdoctors";

    /***
     * 预约检查-提交URL
     */
    public static final String ADD_CHECK_URL = BASE_API_URL + "/interface/patient/mycheck/add";
    /**
     * 获取医生详情URL
     */
    public static final String GET_DOCTOR_DETAIL_URL = BASE_API_URL + "/interface/patient/appointment/getdoctordetail";
    /***
     * 手机号码 身份验证URL
     */
    public static final String AUTH_PHONE_WITH_CODE_URL = BASE_API_URL + "/interface/patient/user/authmobile";
    /***
     * 修改密码URL
     */
    public static final String MODIFY_PWD_URL = BASE_API_URL + "/interface/patient/user/updatepassword";
    /**
     * 获取常用就诊人列表URL
     */
    public static final String GET_USER_PATIENT_LIST_URL = BASE_API_URL + "/interface/patient/userlist/getlist";

    /**
     * 添加常用就诊人URL
     */
    public static final String ADD_USER_PATIENT_URL = BASE_API_URL + "/interface/patient/userlist/add";

    /***
     * 添加收货地址
     */
    public static final String ADD_ADDRESS_URL = BASE_API_URL + "/interface/patient/ recieveaddress/add";
    /**
     * 修改收货地址
     */
    public static final String MODIFY_ADDRESS_URL = BASE_API_URL + "/interface/patient/recieveaddress/edit";
    /**
     * 修改常用就诊人
     */
    public static final String MODIFY_PATIENT_URL = BASE_API_URL + "/interface/patient/userlist/edit";
    /***
     * 获取地址列表URL
     */
    public static final String GET_ADDRESS_LIST_URL = BASE_API_URL + "/interface/patient/recieveaddress/getlist";

    /***
     * 获取首页顶部广告列表URL
     */
    public static final String GET_HOME_AD_LIST_URL = BASE_API_URL + "/interface/patient/data/information/getindex";
    /***
     * 我的咨询列表URL
     */
    public static final String GET_USER_COUNSEL_LIST_URL = BASE_API_URL + "/interface/patient/myconsultation/getlist";
    /**
     * 我的咨询详情URL
     */
    public static final String GET_USER_COUNSEL_DETAIL_URL = BASE_API_URL + "/interface/patient/myconsultation/getdetail";
    /**
     * 我的病历详情URL
     */
    public static final String GET_USER_MEDICAL_DETAIL_URL = BASE_API_URL + "/interface/patient/myrecord/getdetail";

    /***
     * 我的处方详情URL
     */
    public static final String GET_USER_RECIPE_DETAIL_URL = BASE_API_URL + "/interface/patient/myprescription/getdetail";
    /***
     * 我的报告详情URL
     */
    public static final String GET_USER_REPORT_DETAIL_URL = BASE_API_URL + "/interface/patient/myreport/getdetail";
    /***
     * 我的订单详情URL
     */
    public static final String GET_USER_ORDER_DETAIL_URL = BASE_API_URL + "/interface/patient/myorder/getdetail";
    /***
     *    我的检查详情URL
     */
    public static final String GET_USER_CHECK_DETAIL_URL = BASE_API_URL + "/interface/patient/mycheck/getdetail";
    /***
     *  智能导诊-获取症状起始问题 URL
     */
    public static final String GET_SYMPTOM_CHECK_FIRST_URL = BASE_API_URL + "/interface/patient/selfcheck/getfirst";
    /***
     * 智能导诊-获取下一步流程URL
     */
    public static final String GET_SYMPTOM_CHECK_NEXT_URL = BASE_API_URL + "/interface/patient/selfcheck/getnext";

    /**
     * 用户注册URL
     */
    public static final String REGISTER_URL = BASE_API_URL + "/public/patient/user/register";
    /**
     * 我的订单列表URL
     */
    public static final String USER_ORDER_LIST = BASE_API_URL + "/interface/patient/myorder/getlist";
    /***
     * 删除我的订单URL
     */
    public static final String DELETE_USER_ORDER_URL = BASE_API_URL + "/interface/patient/myorder/remove";
    /***
     * 删除收货地址
     */
    public static final String DELETE_ADDRESS_URL = BASE_API_URL + "/interface/patient/recieveaddress/delete";
    /***
     * 删除常用就诊人地址
     */
    public static final String DELETE_PATIENT_URL = BASE_API_URL + "/interface/patient/userlist/del";
    /***
     * 我的病历列表URL
     */
    public static final String USER_MEDICAL_LIST_URL = BASE_API_URL + "/interface/patient/myrecord/getlist";
    /**
     * 根据患者id，医生id获取病历授权信息
     */
    public static final String GET_PATIENT_RECORD_LIST_URL = BASE_API_URL + "/interface/patient/appointment/getrecords";
    /***
     * 便捷检查-获取科室检查项目
     */
    public static final String GET_DEPART_CHECK_LIST_URL = BASE_API_URL + "/interface/medicaldata/check/getall";
    /**
     * 根据搜索关键字进行药品的搜索
     */
    public static final String SEARCH_MEDICAL_URL = BASE_API_URL + "/interface/medicaldata/drug/getall";
    /***
     * 按方抓药，提交审核后的获取订单信息URL
     */
    public static final String CONFIRM_ORDER_URL = BASE_API_URL + "/interface/patient/mall/confirm";
    /***
     * 按方抓药，提交审核后提交订单
     */
    public static final String SAVE_ORDER_URL = BASE_API_URL + "/interface/patient/mall/saveconfirm";
    /***
     * 支付订单-修改订单状态
     */
    public static final String PAY_ORDER_URL = BASE_API_URL + "/interface/patient/mall/changeorder";
    /***
     * 科室医生-医生详情-添加关注接口URL
     */
    public static final String ADD_ATTENTION_URL = BASE_API_URL + "/interface/patient/appointment/attention";
    /**
     * 我的报告列表URL
     */
    public static final String USER_REPORT_LIST_URL = BASE_API_URL + "/interface/patient/myreport/getlist";
    /**
     * 我的检查列表URL
     */
    public static final String USER_CHECK_LIST_URL = BASE_API_URL + "/interface/patient/mycheck/getlist";
    /**
     * 我的处方列表URL
     */
    public static final String USER_RECIPE_LIST_URL = BASE_API_URL + "/interface/patient/myprescription/getlist";
    /***
     * 按方抓药-提交审核
     */
    public static final String SAVE_PRESCRIPTION_URL = BASE_API_URL + "/interface/patient/mall/submitaudit";
    /**
     * 预约挂号-保存
     */
    public static final String SUBMIT_SUBSCRIBE_URL = BASE_API_URL + "/interface/patient/appointment/submit";
    /**
     * 预约加号-保存URL
     */
    public static final String SUBMIT_APPOINTMENT_ADD_URL = BASE_API_URL + "/interface/patient/appointment/addplus";
    /***
     * 我的病历添加URL
     */
    public static final String SUBMIT_ADD_MEDICAL_RECORD_URL = BASE_API_URL + "/interface/patient/myrecord/add";
    /***
     * 我的报告添加URL
     */
    public static final String SUBMIT_ADD_REPORT_URL = BASE_API_URL + "/interface/patient/myreport/add";
    /***
     * 我的处方添加URL
     */
    public static final String SUBMIT_ADD_RECIPE_URL = BASE_API_URL + "/interface/patient/myprescription/add";
    /***
     * 在线咨询URL
     */
    public static final String ONLINE_COUNSEL_URL = BASE_API_URL + "/interface/patient/appointment/onlineconsult";
    /***
     * 我的咨询-咨询详情-追问URL
     */
    public static final String USER_COUNSEL_QUESTION_URL = BASE_API_URL + "/interface/patient/myconsultation/add";

    /***
     * 校验和修改绑定手机号码
     */
    public static final String VERIFY_MODIFY_BIND_PHONE_URL = BASE_API_URL + "/interface/patient/user/authmobile";
    /***
     * 根据给定的手机号码发送验证码
     */
    public static final String SEND_SMS_CODE_URL = BASE_API_URL + "/public/sms/sendValided";

    /**
     * 我的医生列表URL
     */
    public static final String USER_DOCTOR_LIST_URL = BASE_API_URL + "/interface/patient/mydoctor/getlist";
    /***
     * 我的预约列表URL
     */
    public static final String USER_SUBSCRIBE_LIST_URL = BASE_API_URL + "/interface/patient/myappointment/getlist";
    /***
     * 我的预约详情URL
     */
    public static final String USER_APPOINTMENT_DETAIL_URL = BASE_API_URL + "/interface/patient/myappointment/getdetail";
    /***
     * 获取医生评价详情
     */
    public static final String GET_USER_DOCTOR_EVALUATION_URL = BASE_API_URL + "/interface/patient/doctorevaluation/getdetail";
    /***
     * 提交医生评价
     */
    public static final String ADD_USER_DOCTOR_EVALUATION_URL = BASE_API_URL + "/interface/patient/doctorevaluation/add";

    /**
     * 我的订单搜索URL
     */
    public static final String USER_ORDER_SEARCH = BASE_API_URL + "/interface/patient/myorder/search";

    /**
     * 在线售药首页URL
     */
    public static final String ONLINE_URL = BASE_API_URL + "/interface/patient/mall/getindex";
}
