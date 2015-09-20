package com.xinheng.mvp.model.user;

import com.xinheng.mvp.model.BaseEmptyItem;

/**
 * 我的处方信息
 */
public class UserRecipeItem extends BaseEmptyItem
{
    /**
     * 处方id
     */
    public String id;
    /**
     * 处方名称
     */
    public String name;
    /**
     * 处方付费状态 type=0时出现  type=1 没有此字段
     */
    public String payState;
    /**
     * 处方类型 0 医院同步处方 1患者上传处方
     */
    public String type;


    public static final String DEBUG_SUCCESS = "{\"message\":\"获取数据成功\",\"properties\":[{\"id\":\"3\",\"name\":\"处方\",\"payState\":\"1\",\"type\":\"0\"},{\"id\":\"4\",\"name\":\"处方2\" ,\"type\":\"1\"}],\"result\":\"1\"}";
    public static final String DEBUG_FAILURED = "{\"result\":\"-1\",\"message\":\"获取检查报告列表信息失败！\"}";


} 
