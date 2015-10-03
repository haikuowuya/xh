package com.xinheng.mvp.model.user;

import com.xinheng.mvp.model.BaseEmptyItem;

import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/20 0020
 * 时间： 16:40
 * 说明： 个人中心-我的咨询回复信息
 */
public class UserCounselReplyItem extends BaseEmptyItem
{
    /**
     * 咨询回复医生id
     */
    public String  doctId;
    /**
     * 咨询回复医生姓名
     */
    public String   doctName;
    /***
     * 回复医生医院
     */
    public String   hospital;
    /***
     * 回复医生科室
     */
    public String  department;
    /**
     * 回复医生职称
     */
    public String  technicalPost;
    /**
     * 回复内容列表
     */
    public List<ListItem> list;
    public static final String  DEBUG_SUCCESS="{\"result\":\"1\",\"message\":\"获取咨询回复信息成功\",\"propertise\":[{\"doctId\":\"7\",\"doctorName\":\"朱震\",\"hospital\":\"苏州中医院\",\"department\":\"外科\",\"technicalPost\":\"主任医师\",\"content\":\"季节交替，天气变化反常，可能为感冒引起的症状，建议到..\",\"createTime\":\"22222222222\"},{\"doctId\":\"7\",\"doctorName\":\"朱震\",\"hospital\":\"苏州中医院\",\"department\":\"外科\",\"technicalPost\":\"主任医师\",\"content\":\"季节交替，天气变化反常，可能为感冒引起的症状，建议到..\",\"createTime\":\"22222222222\"}]}";
    public static final String  DEBUG_FAILURED="{\"result\":\"-1\",\"message\":\"获取咨询回复信息失败！\"}";


    public  static  class  ListItem extends  BaseEmptyItem
    {
        public  String content;
        public  String createTime;
        public  String isUser;
    }


}
