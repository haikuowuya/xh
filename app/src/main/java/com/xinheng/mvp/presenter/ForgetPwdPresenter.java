package com.xinheng.mvp.presenter;

/**
 * 忘记密码接口
 */
public interface ForgetPwdPresenter
{
    /***
     * 验证帐号
     *
     * @param account
     */
    public void doAuthAccount(String account);

    /***
     * 验证手机号码
     *
     * @param phone ：手机号码
     * @param code  ：验证码
     */
    public void doAuthPhone(String phone, String code);

    /***
     * 验证常用就诊人
     *
     * @param account ：帐号
     * @param patient ：常用就诊人
     */
    public void doAuthPatient(String account, String patient);

    /***
     * 重置密码
     *
     * @param account ：帐号
     * @param newPwd  ：新密码
     */
    public void doResetPwd(String account, String newPwd);
}
