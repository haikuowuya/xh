package com.xinheng.mvp.model;

import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/20 0020
 * 时间： 17:04
 * 说明： 科室导航 - 科室信息
 */
public class DepartItem extends BaseEmptyItem
{
    /**
     * 科室id
     */
    public String id;
    /**
     * 科室名字
     */
    public String name;
    /**
     * 子科室信息
     */
    public List<DepartItem> childs;



    public static final String  DEBUG_SUCCESS="{\"result\":\"1\",\"message\":\"手机号修改成功\"}";
    public static final String  DEBUG_FAILURED="{\"result\":\"-1\",\"message\":\"获取科室信息失败！\"}";



}
