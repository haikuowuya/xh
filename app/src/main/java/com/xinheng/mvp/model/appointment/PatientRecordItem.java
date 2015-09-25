package com.xinheng.mvp.model.appointment;

import com.xinheng.mvp.model.BaseEmptyItem;

/**
 * Created by Steven on 2015/9/24 0024.
 */
public class PatientRecordItem extends BaseEmptyItem
{
    public String bmrId;//病历id
    public String doctorName;//医生名称
    public String departName;//科室名称
    public String createDate;
    public String createTime;
    public String isOpen;

    public static final String DEBUG_SUCCESS = "{\"message\":\"获取信息成功!\",\"properties\":[{\"bmrId\":\"1\",\"createDate\":1437580800000,\"departName\":\"心内科3\",\"doctorName\":\"宋宁静\",\"isOpen\":\"1\"}],\"result\":\"1\"}";
}
