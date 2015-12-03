package com.xinheng.mvp.model;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/24 0024
 * 时间： 15:33
 * 说明：登录实体类
 */
public class LoginItem extends BaseEmptyItem
{
    /**
     * 账号或手机号，必选
     */
    public String account;

    /**
     * 密码，必选
     */
    public String  password;

    public LoginItem()
    {
    }

    public LoginItem(String account, String password)
    {
        this.account = account;
        this.password = password;
    }
}
