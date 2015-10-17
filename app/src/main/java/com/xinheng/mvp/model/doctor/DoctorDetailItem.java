package com.xinheng.mvp.model.doctor;

import com.xinheng.mvp.model.BaseEmptyItem;

import java.util.List;

/**
 * 科室医生详情
 */
public class DoctorDetailItem extends BaseEmptyItem
{
    public String doctId;//医生id
    public String doctName;//医生姓名
    public String img;//医生头像
    public String hospital;//医院
    public String department;//医生科室
    public String technicalPost;//医生职称
    public String skill;//医生擅长技能
    public String introduction;//医生介绍
    public String isAttention     ;//是否关注
    public List<DoctorScheduleItem> schedule;
    public static final String DEBUG_SUCCESS = "{\"message\":\"获取科室医生信息成功!\",\"properties\":{\"department\":\"眼科\",\"doctId\":\"40288af44f7870fa014f788f79fa0015\",\"doctName\":\"宋宁静\",\"introduction\":\"硕士研究生导师。师从于著名针灸学家、中国工程院院士石学敏教授、中医世家马融教授。曾随石学敏院士临床工作，并颇得亲传。在武警卫生系统率先引进“石氏中风单元疗法”，以“醒脑开窍”针刺法为主，配合中药、康复锻炼等，治疗不同时期的中风病、及颅脑外伤后遗症等病症，手法独特，且为患者制定个体治疗方案，创立“个体综合康复”疗法，在脑血管疾病和脑外伤术后的治疗中取得了明显临床疗效，形成了独特的治疗体系，疗效显著。使患者能收到明显近期及远期疗效，降低了致残率，极大限度地提高了患者的生活质量。作为专家多次到全国各地技术指导。\",\"schedule\":[{\"address\":\"眼科门诊二诊室\",\"begintime\":\"08:30\",\"date\":\"2015-08-30\",\"endtime\":\"16:30\",\"fee\":0,\"status\":\"1\",\"type\":\"专家\"},{\"address\":\"眼科门诊二诊室\",\"begintime\":\"13:30\",\"date\":\"2015-08-31\",\"endtime\":\"18:00\",\"fee\":0,\"status\":\"1\",\"type\":\"专家\"},{\"address\":\"眼科门诊二诊室\",\"begintime\":\"12:30\",\"date\":\"2015-09-01\",\"endtime\":\"17:30\",\"fee\":0,\"status\":\"1\",\"type\":\"专家\"},{\"address\":\"眼科门诊二诊室\",\"begintime\":\"11:00\",\"date\":\"2015-09-02\",\"endtime\":\"15:30\",\"fee\":0,\"status\":\"1\",\"type\":\"专家\"}],\"skill\":\" 用针药结合、及运用刺络疗法治疗面瘫、面肌痉挛、颈腰椎病、坐骨神经痛、头痛、三叉神经痛、诸神经痛、软组织损伤等病症；并运用中药治疗心肌缺血、失眠、房颤等老年病、慢... 更多 擅长：用针药结合、及运用刺络疗法治疗面瘫、面肌痉挛、颈腰椎病、坐骨神经痛、头痛、三叉神经痛、诸神经痛、软组织损伤等病症；并运用中药治疗心肌缺血、失眠、房颤等老年病、慢性病亦有独道经验。以“醒脑开窍”针刺法为主，治疗不同时期的中风病、及颅脑外伤后遗症等病症。\",\"technicalPost\":\"主任医师\"},\"result\":\"1\"}" ;

}
