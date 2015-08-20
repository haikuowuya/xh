package com.xinheng.mvp.model;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/20 0020
 * 时间： 16:31
 * 说明：修改手机号码信息
 */
public class ModifyPhoneItem extends BaseEmptyItem
{
    /**
     * 账户
     */
    public String account;
    /**
     * 原始密码
     */
    public String password;
    /**
     * 新手机号
     */
    public String newmobile;
    /**
     * 验证码
     */
    public String checkcode;

    public static final String  DEBUG_SUCCESS="{\"result\":\"1\",\"message\":\"手机号修改成功\"}";
    public static final String  DEBUG_FAILURED="{\"result\":\"-1\",\"message\":\"手机号修改失败\"}";


}
