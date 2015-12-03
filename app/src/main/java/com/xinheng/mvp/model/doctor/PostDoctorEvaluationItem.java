package com.xinheng.mvp.model.doctor;

/**
 * 对医生评价时向服务器端发送的post请求体
 */
public class PostDoctorEvaluationItem extends BasePostDoctorItem
{
   // public String userId;//用户id 必选
    public String serviceGrade;//服务分 必选
    public String serviceDescribe;//服务描述
    public String effectGrade;//疗效分 必选
    public String effectDescribe;//疗效描述
    public String feeGrade;//费用分 必选
    public String feeDescribe;//费用描述

}
