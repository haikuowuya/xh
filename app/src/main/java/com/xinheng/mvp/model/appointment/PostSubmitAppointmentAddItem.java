package com.xinheng.mvp.model.appointment;

import com.xinheng.mvp.model.BaseEmptyItem;

import java.io.File;
import java.util.List;

/**
 * 提交预约加号时向服务器的发送的post请求体
 */

public class PostSubmitAppointmentAddItem extends BaseEmptyItem
{
    public String userId;//用户id （加密）
    public String scheduleId;//医生排班id（加密）
    public String sex;//就诊人id （加密）
    public String age;//0：初诊 1：复诊
    public String conditionReport;//病情自述（加密）
    public String symptoms;//症状自述（加密）
    public String message;//患者留言
    public String checkcode;//短信验证码
    public List<File> files;//图片组以文件流方式提交
}
