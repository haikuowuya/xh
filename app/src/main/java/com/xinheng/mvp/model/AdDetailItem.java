package com.xinheng.mvp.model;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/20 0020
 * 时间： 16:37
 * 说明：滚动资讯详情信息
 */
public class AdDetailItem extends BaseEmptyItem
{
    /**
     * 资讯id
     */
    public String id;
    /**
     * 标题
     */
    public String title;
    /**
     * 图片
     */
    public String img;
    /**
     * 资讯内容
     */
    public String content;
    /**
     * 创建时间
     */
    public String createTime;

    public static final String  DEBUG_SUCCESS="{\"result\":\"1\",\"message\":\"获取资讯成功\",\"propertise\":{\"id\":\"1\",\"title\":\"Tom\",\"img\":\"1.png\",\"content\":\"资讯详细内容\",\"createTime\":\"5689445665\"}}";
    public static final String  DEBUG_FAILURED="{\"result\":\"-1\",\"message\":\"获取资讯失败！\"}";


}
