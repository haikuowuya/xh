package com.xinheng.mvp.model;

/**
 * 登录成功后的用户信息
 */
public class LoginSuccessItem extends BaseEmptyItem
{
    /**
     * 用户id
     */
    public String id;
    /**
     * 上次登录时间
     */
    public String lastlogintime;

    /**
     * 手机号码
     */
    public String mobile;

    /**
     * 用户姓名
     */
    public String name;

    /***
     * 访问的sessionId
     */
    public String sessionId;

    /***
     * 用户的头像URL
     */
    public String photo;
    /***
     * 账户信息
     */
    public AccountItem account;

    @Override
    public String toString()
    {

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("lastlogintime = " + lastlogintime);
        stringBuffer.append(" mobile = " + mobile);
        stringBuffer.append(" photo = " + photo);
        return stringBuffer.toString();
    }

    public static class AccountItem extends BaseEmptyItem
    {
        public String id;//用户id
        public String nickname;//用户昵称
        public String account;//账户
        public String photo;//头像
        public String mobile;//手机号码
    }
}
