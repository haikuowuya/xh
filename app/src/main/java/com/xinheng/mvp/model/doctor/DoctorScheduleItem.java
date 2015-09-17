package com.xinheng.mvp.model.doctor;

import com.xinheng.mvp.model.BaseEmptyItem;

/**
 * 科室医生排班信息
 */
public class DoctorScheduleItem extends BaseEmptyItem
{
   public String  type;//出诊类型
    public String date;//出诊日期
    public String begintime;//坐诊开始时间
    public String endtime;//坐诊结束时间
    public String address;//坐诊地址
}
