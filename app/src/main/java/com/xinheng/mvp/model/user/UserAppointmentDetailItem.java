package com.xinheng.mvp.model.user;

import com.xinheng.mvp.model.BaseEmptyItem;
import com.xinheng.mvp.model.appointment.PatientRecordItem;
import com.xinheng.mvp.model.doctor.DoctorScheduleItem;

import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/20 0020
 * 时间： 16:52
 * 说明： 我的预约详情信息
 */
public class UserAppointmentDetailItem extends BaseEmptyItem
{
    public String id;//预约信息id
    public String doctName;//医生姓名
    public String photo;//医生头像
    public String technicalPost;//医生职称
    public String hospiatal;//医院
    public List<String> imgs;
    public String department;//科室
    public List<PatientRecordItem> authrecord;

    /***
     * 预约信息
     */
    public DoctorScheduleItem schedule;
    /**
     * 就诊人信息
     */
    public PatientItem patient;

    public static final class PatientItem extends BaseEmptyItem
    {
        public String name;//患者姓名
        public String tag;//患者就诊类型 0 初诊 1复诊
    }
}
