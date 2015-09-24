package com.xinheng.mvp.model.subscribe;

import com.xinheng.mvp.model.BaseEmptyItem;

import java.io.File;
import java.util.List;

/**
 * 提交预约时向服务器的发送的post请求体
 */
public class PostSubmitSubscribeItem extends BaseEmptyItem
{
    public String userId;//用户id（加密）
    public String scheduleId;//医生排班id（加密）
    public String patientId;//就诊人id （加密）
    public String status;//0：初诊 1：复诊
    public String conditionReport;//病情自述（加密）
    public String symptoms;//症状自述（加密）
    public List<String> bmrIds;//病历id（加密）
    public List<String> auths;//是否授权 0:不授权 1：授权
    public List<File>  files;//图片组以文件流方式提交
}
