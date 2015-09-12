package com.xinheng.mvp.model;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/21 0021
 * 时间： 16:18
 * 说明：  我的报告信息
 */
public class UserChecktItem extends BaseEmptyItem
{
    /**
     * 等待审核
     */
    public static final String  STATUS_0="0";
    /**
     * 审核成功
     */
    public static final String  STATUS_1="1";
    /**
     * 审核失败
     */
    public static final String  STATUS_2="2";
    /**
     * 检查id
     */
   public String id;
    /**
     * 检查名称
     */
    public String  name;
    /**
     *   我的便捷检查审核状态 0审核中 1审核通过 2审核失败 type = 1时出现
     */
    public String  consentState;
    /**
     * 处方付费状态 type=0是出现  type=1 没有此字段
     */
    public String  payState;
    /**
     *  检查类型 0 医院同步检查 1患者上传我的便捷检查
     */
    public String  type;

    public static final String  DEBUG_SUCCESS="{\"message\":\"获取数据成功\",\"properties\":[{\"id\":\"1\",\"name\":\"体检\",\"payState\":\"1\",\"type\":\"0\"}],\"result\":\"1\"}";

    public static final String  DEBUG_FAILURED="{\"result\":\"-1\",\"message\":\"获取检查报告列表信息失败！\"}";


}
