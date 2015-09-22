package com.xinheng.eventbus;

/**
 * Created by Steven on 2015/9/22 0022.
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
