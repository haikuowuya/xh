package com.xinheng.mvp.model.online;

import com.xinheng.mvp.model.BaseEmptyItem;

import java.util.List;

/**
 *  在线售药中间的数据
 */
public class OnLineCenterItem extends BaseEmptyItem
{

    public  String id;
    public  String name;
    public List<HomeOnLineItem.Item> items;
}
