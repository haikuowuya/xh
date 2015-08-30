package com.xinheng.mvp.model;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/20 0020
 * 时间： 16:25
 * 说明： 用户注册信息
 */
public class RegisterItem extends BaseEmptyItem
{
    /**
     * 密码，必选
     */
    public String password;
    /**
     * 手机号，必选
     */
    public String mobile;
    /**
     * 手机验证码，必选
     */
    public String checkcode;

    public RegisterItem()
    {
    }

    public RegisterItem( String mobile,String password, String checkcode)
    {
        this.mobile = mobile;
        this.password = password;
        this.checkcode = checkcode;
    }

    public static final String  DEBUG_SUCCESS="{\"result\":\"1\",\"message\":\"注册成功\"}";
    public static final String  DEBUG_FAILURED="{\"result\":\"-1\",\"message\":\"注册失败！\"}";

}
