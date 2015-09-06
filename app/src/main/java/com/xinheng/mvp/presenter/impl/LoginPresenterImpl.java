package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.LoginItem;
import com.xinheng.mvp.presenter.LoginPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.MD5;
import com.xinheng.util.RSAUtil;

/**
 * 作者： libo
 * 日期： 2015/8/24 0024
 * 时间： 16:34
 * 说明：用户登录的实现类
 */
public class LoginPresenterImpl implements LoginPresenter
{
    private BaseActivity mActivity;

    private DataView mDataView;

    public LoginPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    @Override
    public void doLogin(String username, String password)
    {
        String pwd = new MD5().getMD5_32(password);
        LoginItem loginItem = new LoginItem(username, pwd);
        final String jsonLoginItem = GsonUtils.toJson(loginItem);
        //加密的字符串
        final String encryptString = RSAUtil.clientEncrypt(jsonLoginItem);
        System.out.println("加密后的字符串 = "+ encryptString);
        String loginUrl = APIURL.LOGIN_URL;
        RequestUtils.getDataFromUrlByPost(mActivity, loginUrl, encryptString, mDataView);
    }
}
