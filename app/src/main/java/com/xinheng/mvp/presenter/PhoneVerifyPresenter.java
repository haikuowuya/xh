package com.xinheng.mvp.presenter;

/**
 * Created by Steven on 2015/9/17 0017.
 */
public interface PhoneVerifyPresenter
{
    public  static  final  int TYPE_OLD_PHONE=0;
    public  static  final  int TYPE_NEW_PHONE=1;
    public void doVerifyPhone(String phone, String checkCode);
}
