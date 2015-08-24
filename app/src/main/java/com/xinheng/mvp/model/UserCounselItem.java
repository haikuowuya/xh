package com.xinheng.mvp.model;

import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/20 0020
 * 时间： 16:40
 * 说明： 个人中心-我的咨询信息
 */
public class UserCounselItem extends BaseEmptyItem
{
    /**
     * 咨询id
     */
    public String id;
    /**
     * 标题
     */
    public String title;
    /**
     * 问题
     */
    public String question;
    /**
     * 咨询创建时间
     */
    public String createTime;
    /**
     * 总回答数
     */
    public String total;
    /**
     * 咨询回复内容
     */
    public List<UserCounselReplyItem> replys;


    public static final String DEBUG_SUCCESS = "{\"result\":\"1\",\"message\":\"获取信息成功\",\"properties\":[{\"id\":\"4\",\"title\":\"5\",\"question\":\"全身酸痛无力，食欲不振，恶心反胃，病情持续一周左右。\",\"createTime\":\"111111111111\",\"total\":\"4\",\"replys\":[{\"doctId\":\"7\",\"doctorName\":\"朱震\",\"hospital\":\"苏州中医院\",\"department\":\"外科\",\"technicalPost\":\"主任医师\",\"content\":\"季节交替，天气变化反常，可能为感冒引起的症状，建议到..\",\"createTime\":\"22222222222\"}]}]}";
    public static final String DEBUG_FAILURED = "{\"result\":\"-1\",\"message\":\"获取咨询信息失败！\"}";

}
