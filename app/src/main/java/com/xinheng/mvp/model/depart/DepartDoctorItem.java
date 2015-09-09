package com.xinheng.mvp.model.depart;

import com.xinheng.mvp.model.BaseEmptyItem;

/**
 * Created by Administrator on 2015/9/9 0009.
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
    public String doctorName;
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

    public static final String DEBUG_SUCCESS = "{\"result\":\"1\",\"message\":\"获取科室医生列表信息成功\",\"properties\":[{\"doctId\":\"6\",\"status\":\"0\",\"doctorName\":\"朱震\",\"img\":\"doc.jpg\",\"hospital\":\"苏州中医院\",\"department\":\"外科\",\"technicalPost\":\"主任医师\",\"skill\":\"擅长：治疗瘿气、瘿病眼病、瘿痈、消渴病及其慢性并发症、痹症、绝经前后诸症、肥胖症、肾上腺疾病、性腺疾病等内分泌疾病\"},{\"doctId\":\"443\",\"status\":\"0\",\"doctorName\":\"代芳\",\"img\":\"doc.jpg\",\"hospital\":\"苏州中医院\",\"department\":\"外科\",\"technicalPost\":\"主任医师\",\"skill\":\"擅长：治疗瘿病、消渴病及其慢性并发症、痹症、绝经前后诸症、肥胖症等内分泌疾病，尤其致力于瘿气及瘿病眼病方向的研究\"}]}";
    public static final String DEBUG_FAILURED = "{\"result\":\"-1\",\"message\":\"获取科室医生列表信息失败！\"}";

}
