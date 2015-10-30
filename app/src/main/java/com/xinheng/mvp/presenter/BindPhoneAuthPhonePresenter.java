package com.xinheng.mvp.presenter;

/**
 *  绑定手机号码验证 验证手机号码 接口
 */
public interface BindPhoneAuthPhonePresenter
{
    public  void  doAuthOldPhone(String phone, String code);

    public void doAuthNewPhone(String oldPhone , String newPhone , String newPhoneCode);
}
