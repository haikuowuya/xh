package com.xinheng.mvp.presenter;

/**
 * 注册接口
 */
public interface RegisterPresenter
{
    /**
     * 用户注册
     *
     * @param mobile 手机号码
     * @param pwd    密码
     * @param code   验证码
     */
    public void doRegister(String mobile, String pwd, String code);
}
