package com.xinheng.mvp.model;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/21 0021
 * 时间： 16:18
 * 说明：  我的病历详情信息
 */
public class UserMedicalDetailItem extends BaseEmptyItem
{
    /**
     * 病历信息id
     */
    public String id;
    /**
     * 病历名称
     */
    public String name;
    /**
     * 病历类型  0 医院同步 1患者上传
     */
    public String type;

    public static final String  DEBUG_SUCCESS="{\"message\":\"获取数据成功\",\"properties\":[{\"id\":\"1\",\"name\":\"2015-08-12 苏州中医院 内科\",\"type\":\"0\"}],\"result\":\"1\"}";
    public static final String  DEBUG_FAILURED="{\"result\":\"-1\",\"message\":\"获取病历信息列表失败！\"}";


}
