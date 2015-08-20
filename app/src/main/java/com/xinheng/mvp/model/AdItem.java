package com.xinheng.mvp.model;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/17 0017
 * 时间： 17:59
 * 说明： 首页滚动资讯信息
 */
public class AdItem extends BaseEmptyItem
{
    /**
     *  资讯id
     */
   public String  id;
    /**
     *  标题
     */
    public String   title;
    /**
     *  图片
     */
    public String img;

    public AdItem(String img)
    {
        this.img = img;
    }


    public static final String  DEBUG_SUCCESS=" {\"result\":\"1\",\"message\":\"获取资讯成功\",\"propertise\":{\"id\":\"1\",\"title\":\"Tom\",\"img\":\"1.png\"}}";
    public static final String  DEBUG_FAILURED="{\"result\":\"-1\",\"message\":\"获取信息失败！\"}";


}
