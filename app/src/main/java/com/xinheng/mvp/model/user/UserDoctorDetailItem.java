package com.xinheng.mvp.model.user;

/**
 * 获取我的医生详情时向服务器端发送的post请求体
 */
public class UserDoctorDetailItem extends UserDoctorItem
{

    /**
     * 医院名称
     */
    public String institution;

    /**
     * 服务分
     */
    public String serviceGrade;
    /**
     * 疗效分
     */
    public String effectGrade;
    /**
     * 费用分
     */
    public String feeGrade;

}
