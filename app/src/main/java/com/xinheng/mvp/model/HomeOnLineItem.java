package com.xinheng.mvp.model;

import java.util.List;

/**
 *  在线售药的首页数据
 */
public class HomeOnLineItem extends  BaseEmptyItem
{
    /**
     * 顶部的广告
     */
    public List<AdItem> advertisement;
    /**
     * 中间数据
     */
    public List<OnLineCenterItem> subject;
    /**
     * 底部数据
     */
    public List<OnLineBottomItem> list;

    /**
     * 在线售药首界面中间和底部每一个Item的数据
     */
    public static class  Item
    {
        public  String id;
        public String drugId;
        public  String title;
        public  String img;
    }
}
