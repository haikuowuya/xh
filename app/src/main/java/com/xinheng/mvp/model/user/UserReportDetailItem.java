package com.xinheng.mvp.model.user;

import com.xinheng.mvp.model.BaseEmptyItem;

import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/21 0021
 * 时间： 16:18
 * 说明：  我的报告详情信息
 */
public class UserReportDetailItem extends BaseEmptyItem
{
    /***
     * 0医院同步报告
     */
    public static final String TYPE_0 = "0";
    /***
     * 1用户自己上传报告
     */
    public static final String TYPE_1 = "1";
    public String id;//检查报告id
    public String name;//检查报告名称
    public String institution;//医疗机构名称
    public String checkTime;//检查日期
    public String type;//文字报告类型 0医院同步报告 1用户自己上传报告
    public List<String> reportimgs;//图片报告图片

    //医院同步的报告才有的内容


    public String  itemName;//检查项目名称

    public String  patient;//患者姓名
    public String  doctor;//送检医生
    public String   checkPersonName;//检验员
    public String   report;//文字报告内容
    public String   conclusion;//结论
    public String  reportDate;//报告日期

}
