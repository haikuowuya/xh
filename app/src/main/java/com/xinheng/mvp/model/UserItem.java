package com.xinheng.mvp.model;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/20 0020
 * 时间： 16:21
 * 说明：个人用户信息
 */
public class UserItem extends BaseEmptyItem
{
    /**
     * 用户唯一标识id
     */
    public String id;

    /***
     * 用户账号
     */
    public String name;
    /**
     * 用户头像
     */
    public String photo;
    /**
     * 手机号
     */
    public String mobile;
    /**
     * 上一次登录时间
     */
    public String lastlogintime;
    /**
     * 用户登录sessionId
     */
    public String sessionId;


    public static final String  DEBUG_SUCCESS="{\"result\":\"1\",\"message\":\"登入成功\",\"propertise\":{\"id\":\"1\",\"name\":\"Tom\",\"photo\":\"1.png\",\"mobile\":\"1346455688\",\"lastlogintime\":\"5689445665\",\"sessionId\":\"5ccd9757-e68f-4069-9064-1c3f1a372295\"}}";
    public static final String  DEBUG_FAILURED="{\"result\":\"-1\",\"message\":\"输入的账号不存在！\"}";


}
