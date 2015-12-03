package com.xinheng.mvp.model.depart;

import com.xinheng.mvp.model.BaseEmptyItem;

/**
 * 科室医生实体类
 */
public class DepartDoctorItem extends BaseEmptyItem
{
    /***
     * 医生id
     */
    public String doctId;
    /***
     * 预约状态    1:有号 0：无号 -1：约满
     */
    public String status;
    /**
     * 医生姓名
     */
    public String doctName;
    /**
     * 医生头像
     */
    public String img;
    /**
     * 医院
     */
    public String hospital;
    /**
     * 医生科室
     */
    public String department;

    /***
     * 医生职称
     */
    public String technicalPost;
    /***
     * 医生擅长技能
     */
    public String skill;

    public static final String DEBUG_SUCCESS = "{\"result\":\"1\",\"message\":\"获取科室医生列表信息成功\",\"properties\":[{\"doctId\":\"40288af44f7870fa014f788f79fa0015\",\"status\":\"0\",\"doctorName\":\"宋宁静\",\"img\":\"doc.jpg\",\"hospital\":\"苏州中医院\",\"department\":\"外科\",\"technicalPost\":\"主任医师\",\"skill\":\"擅长：用针药结合、及运用刺络疗法治疗面瘫、面肌痉挛、颈腰椎病、坐骨神经痛、头痛、三叉神经痛、诸神经痛、软组织损伤等病症；并运用中药治疗心肌缺血、失眠、房颤等老年病、慢... 更多 擅长：用针药结合、及运用刺络疗法治疗面瘫、面肌痉挛、颈腰椎病、坐骨神经痛、头痛、三叉神经痛、诸神经痛、软组织损伤等病症；并运用中药治疗心肌缺血、失眠、房颤等老年病、慢性病亦有独道经验。以“醒脑开窍”针刺法为主，治疗不同时期的中风病、及颅脑外伤后遗症等病症\"}]}";
    public static final String DEBUG_FAILURED = "{\"result\":\"-1\",\"message\":\"获取科室医生列表信息失败！\"}";

}
