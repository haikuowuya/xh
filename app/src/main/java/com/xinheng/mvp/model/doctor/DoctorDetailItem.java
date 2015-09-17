package com.xinheng.mvp.model.doctor;

import com.xinheng.mvp.model.BaseEmptyItem;

import java.util.List;

/**
 * 科室医生详情
 */
public class DoctorDetailItem extends BaseEmptyItem
{
 public  String    doctId;//医生id
    public  String     status;//预约状态    1:有号 0：无号 -1：约满
    public  String    doctName;//医生姓名
    public  String     img;//医生头像
    public  String    hospital;//医院
    public  String     department;//医生科室
    public  String     technicalPost;//医生职称
    public  String     skill;//医生擅长技能
    public  String    introduction;//医生介绍
    public List<DoctorScheduleItem> schedule;

   public  static  final  String DEBUG_SUCCESS="{\"result\":\"1\",\"message\":\"获取医生详细信息成功\",\"properties\":[{\"doctId\":\"6\",\"status\":\"0\",\"doctName\":\"吴雪梅\",\"img\":\"doc.jpg\",\"hospital\":\"苏州中医院\",\"department\":\"外科\",\"technicalPost\":\"主任医师\",\"skill\":\"擅长：治疗瘿气、瘿病眼病、瘿痈、消渴病及其慢性并发症、痹症、绝经前后诸症、肥胖症、肾上腺疾病、性腺疾病等内分泌疾病\",\"introduction\":\"副主任医师，从事针灸工作10余年，现任贵州省针灸学会中风专业委员会委员，贵州省中医药学会脑病专业委员会委员\",\"schedule\":[{\"type\":\"专家\",\"date\":\"2015-05-20\",\"begintime\":\"12:00\",\"endtime\":\"15:00\",\"address\":\" 内科2诊室\"},{\"type\":\"普通\",\"date\":\"2015-05-21\",\"begintime\":\"12:00\",\"endtime\":\"15:00\",\"address\":\" 内科4诊室\"}]}]}";
}
