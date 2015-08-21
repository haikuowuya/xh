package com.xinheng.mvp.model;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/20 0020
 * 时间： 16:55
 * 说明：我的医生信息
 */
public class UserDoctorItem extends BaseEmptyItem
{
    public static final String DEBUG_SUCCESS = "{\"message\":\"获取数据成功\",\"properties\":[{\"department\":\"男性皮肤科\",\"doctId\":\"2\",\"doctName\":\"jack\",\"grade\":\"6.3\",\"hospital\":\"贵州省中医医院\",\"lastServiceTime\":\"1439369998000\",\"technicalPost\":\"主任医师\"},{\"department\":\"男性皮肤科\",\"doctId\":\"1\",\"doctName\":\"tom\",\"grade\":\"5.6\",\"hospital\":\"贵州省中医医院\",\"lastServiceTime\":\"1439354858000\",\"technicalPost\":\"主任医师\"}],\"result\":\"1\"}";
    public static final String DEBUG_FAILURED = "{\"result\":\"-1\",\"message\":\"获取医生列表信息失败！\"}";
    /**
     * 医生id
     */
    public String doctId;
    /**
     * 医生姓名
     */
    public String doctName;
    /**
     * 医院名称
     */
    public String hospital;
    /**
     * 医生科室
     */
    public String department;
    /**
     * 医生职称
     */
    public String technicalPost;
    /**
     * 最后一次就诊时间
     */
    public String lastServiceTime;
    /**
     * 医生评分
     */
    public String grade;

}
