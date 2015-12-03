package com.xinheng.eventbus;

/**
 * 省市地区选择后事件
 */
public class OnProvinceCityAreaSelectEvent extends  BaseOnEvent
{
    public  String province;
    public  String city;
    public  String area;

    public OnProvinceCityAreaSelectEvent(String province, String city, String area)
    {
        this.province = province;
        this.city = city;
        this.area = area;
    }
}
