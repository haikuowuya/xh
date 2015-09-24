package com.xinheng.mvp.model.doctor;

import com.xinheng.mvp.model.BaseEmptyItem;

/**
 * 科室医生排班信息
 */
public class DoctorScheduleItem extends BaseEmptyItem
{
    /**
     * 0：无号
     */
    public static final String STATUS_0 = "0";
    /***
     * 1:有号
     */
    public static final String STATUS_1 = "1";
    /***
     * -1：约满
     */
    public static final String STATUS__1 = "-1";
    /***
     * 排班id
     */
    public String  scheduleId ;
    public String type;//出诊类型
    public String date;//出诊日期
    public String begintime;//坐诊开始时间
    public String endtime;//坐诊结束时间
    public String address;//坐诊地址
    public String fee;//费用
    public String status;//预约状态    1:有号 0：无号 -1：约满
}
