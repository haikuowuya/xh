package com.xinheng.mvp.model;

import com.xinheng.util.Constants;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/25 0025
 * 时间： 16:10
 * 说明：  给服务端传送的数据基类
 */
public class PostItem extends  BaseEmptyItem
{
    public static final String DEFAULT_PAGE ="-1";
    /***
     * 通过id操作的post请求提交
     */
    public String id ;
    public String userId;
    public String page = DEFAULT_PAGE ;
    public String pageSize = Constants.PRE_PAGE_SIZE+"";
}
