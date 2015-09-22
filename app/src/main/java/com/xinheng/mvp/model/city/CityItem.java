package com.xinheng.mvp.model.city;

import com.xinheng.mvp.model.BaseEmptyItem;

import java.util.List;

/**
 * Created by Steven on 2015/9/22 0022.
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
