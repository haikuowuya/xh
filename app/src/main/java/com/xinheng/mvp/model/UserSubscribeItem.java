package com.xinheng.mvp.model;

/**
 *我的预约信息
 */
public class UserSubscribeItem extends  BaseEmptyItem
{
    /**
     *    预约信息id
     */
    public  String id;
    /**
     * 申请时间
     */
    public String createTime;
    /**
     * 预约就诊医生id
     */
    public String doctId;
    /***
     *预约就诊医生姓名
     */
    public String doctName;

    /**
     * 医院名称
     */
    public String  hospital;
    /**
     * 医生科室
     */
    public String department;
    /**
     * 医生职称
     */
    public String technicalPost ;
    /**
     * 预约就诊时间
     */
    public String  appointmentTime;
    /***
     * 医生留言内容
     */
    public String content;






    public static final String  DEBUG_SUCCESS="{\"result\":\"1\",\"message\":\"获取预约信息成功\",\"properties\":[{\"id\":\"327327237667777\",\"createTime\":\"22222222222\",\"doctId\":\"7\",\"doctorName\":\"朱震\",\"hospital\":\"苏州中医院\",\"department\":\"外科\",\"technicalPost\":\"主任医师\",\"appointmentTime\":\"78945489841445\",\"content\":\"根据您提交的病情描述及以往病历分析，初步诊断为扁桃体发炎、脓肿，建议就诊前先到医院检验科\"},{\"id\":\"327327237667455\",\"createTime\":\"22222222222\",\"doctId\":\"7\",\"doctorName\":\"朱震\",\"hospital\":\"苏州中医院\",\"department\":\"外科\",\"technicalPost\":\"主任医师\",\"appointmentTime\":\"78945489841445\",\"content\":\"根据您提交的病情描述及以往病历分析，初步诊断为扁桃体发炎、脓肿，建议就诊前先到医院检验科\"}]}";
    public static final String  DEBUG_FAILURED="{\"result\":\"-1\",\"message\":\"获取预约信息失败！\"}";


}
