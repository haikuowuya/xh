package com.xinheng.mvp.model;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/25 0025
 * 时间： 16:10
 * 说明：  给服务端传送的数据基类
 */
public class PostItem extends  BaseEmptyItem
{
    public static final String DEFAULT_PAGE ="-1";
    public String userId;
    public String page = DEFAULT_PAGE ;
    public String pageSize;
}
