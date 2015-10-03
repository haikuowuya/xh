package com.xinheng.mvp.model.user;

import com.xinheng.mvp.model.BaseEmptyItem;

import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/20 0020
 * 时间： 16:52
 * 说明： 我的预约加号详情信息
 */
public class UserAppointmentAddDetailItem extends BaseEmptyItem
{
   public String  id;//预约信息id
    public String    seeTime;//预约就诊时间
    public String    sex;//性别 0 女 1 男
    public String    age;//年龄
    public String    conditionReport;//病情自述
    public String     symptoms;//症状自述
    public String   patientmessage;//患者留言
    public List<String> imgs;//疾病照片
}
