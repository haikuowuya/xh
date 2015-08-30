package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.RegisterItem;
import com.xinheng.mvp.presenter.RegisterPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 *  注册接口的实现类
 */
public class RegisterPresenterImpl implements RegisterPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;

    public RegisterPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    @Override
    public void doRegister(String mobile, String pwd, String code)
    {
         String registerUrl =   APIURL.REGISTER_URL;
        RegisterItem registerItem = new RegisterItem(mobile, pwd, code);
        final String jsonRegisterItem = GsonUtils.toJson(registerItem);
        System.out.println("注册字符串 = "+ jsonRegisterItem);
        //加密的字符串
        final String encryptString = RSAUtil.clientEncrypt(jsonRegisterItem);
        RequestUtils.getDataFromUrlByPost(mActivity,registerUrl,encryptString,mDataView);
    }
}
