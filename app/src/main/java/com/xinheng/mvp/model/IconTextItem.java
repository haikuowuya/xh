package com.xinheng.mvp.model;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/19 0019
 * 时间： 13:24
 * 说明： 简单的图标文字item
 */
public class IconTextItem extends  BaseEmptyItem
{
    public int iconId;
    public String text;

    public IconTextItem()
    {
    }

    public IconTextItem(int iconId, String text)
    {
        this.iconId = iconId;
        this.text = text;
    }
}
