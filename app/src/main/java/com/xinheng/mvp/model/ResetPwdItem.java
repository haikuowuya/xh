package com.xinheng.mvp.model;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/20 0020
 * 时间： 16:29
 * 说明： 重置密码信息
 */
public class ResetPwdItem extends BaseEmptyItem
{
    /**
     * 账户
     */
    public String account;
    /**
     * 新密码
     */
    public String newpassword;
    /**
     * 验证码
     */
    public String checkcode;


    public static final String  DEBUG_SUCCESS="{\"result\":\"1\",\"message\":\"密码修改成功\"}";
    public static final String  DEBUG_FAILURED="{\"result\":\"-1\",\"message\":\"密码修改失败\"}";


}
