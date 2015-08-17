package com.xinheng.mvp.model;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/17 0017
 * 时间： 18:39
 * 说明：
 */
public class HomeGridItem extends  BaseEmptyItem
{
    public  int resId;
    public String  text;

    public HomeGridItem()
    {
    }

    public HomeGridItem(int resId, String text)
    {
        this.resId = resId;
        this.text = text;
    }
}
