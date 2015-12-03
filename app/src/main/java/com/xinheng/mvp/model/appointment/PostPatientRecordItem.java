package com.xinheng.mvp.model.appointment;

import com.xinheng.mvp.model.BaseEmptyItem;

/**
 *向服务器端提交病历信息的post实体类
 */
public class PostPatientRecordItem extends BaseEmptyItem
{
    public String patientId;//患者id
    public String doctId;//医生id
}
