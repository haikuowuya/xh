package com.xinheng.mvp.model.city;

import com.xinheng.mvp.model.BaseEmptyItem;

import java.util.List;

/**
 * 城市实体类
 */
public class CityItem extends BaseEmptyItem
{
    public String name;
    public List<AreaItem> cityList;

    public static class AreaItem extends BaseEmptyItem
    {
        public String name;
        public List<String> areaList;

    }
}
