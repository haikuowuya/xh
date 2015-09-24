package com.xinheng.mvp.model.doctor;

import com.xinheng.mvp.model.BaseEmptyItem;

/**
 * 根据医生id进行操作，post提交的请求体
 */
public class BasePostDoctorItem  extends BaseEmptyItem
{
    public String doctId;

    public String userId;
}
