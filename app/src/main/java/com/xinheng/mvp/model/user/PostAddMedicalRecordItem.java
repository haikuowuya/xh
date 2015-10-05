package com.xinheng.mvp.model.user;

import com.xinheng.mvp.model.BaseEmptyItem;

import java.io.File;
import java.util.List;

/**
 * 提交病历提交的post请求体
 */
public class PostAddMedicalRecordItem extends BaseEmptyItem
{
    public String seeDate;//就诊时间
    public String patientId;//就诊患者id（加密）
    public String institution;//医院名称
    public String department;//医生科室
    public String doctor;// 医生姓名
    public String record;//诊断记录（加密）
    public List<File> sickfiles;//诊疗图片，文件流提交
    public List<File> reportfiles;//检查报告图片，文件流提交
    public List<File> prescfiles;//处方图片，文件流提交
}
