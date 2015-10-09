package com.xinheng.mvp.presenter.impl;

import android.text.TextUtils;

import com.xinheng.APIURL;
import com.xinheng.LoginActivity;
import com.xinheng.MainActivity;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.LoginSuccessItem;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.presenter.AutoLoginPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.Constants;
import com.xinheng.util.GsonUtils;

/**
 * 作者： libo
 * 日期： 2015/8/24 0024
 * 时间： 16:34
 * 说明：自动登录的实现类
 */
public class AutoLoginPresenterImpl implements AutoLoginPresenter
{
    private BaseActivity mActivity;

    public AutoLoginPresenterImpl(BaseActivity activity)
    {
        mActivity = activity;
    }

    @Override
    public void doAutoLogin()
    {
        /***
         * 参考查看 {@link LoginActivity#onGetDataSuccess(ResultItem, String)}
         */
        String encryptUsernamePwd = mActivity.getPreferences().getString(Constants.PREF_RSA_USERNAME_PWD, null);
        if (TextUtils.isEmpty(encryptUsernamePwd))
        {
            mActivity.showToast("自动登录失败，请重新点击登录按钮登录");
            if(!(mActivity instanceof  LoginActivity))
            {
                LoginActivity.actionLogin(mActivity);
            }
            return;
        }
        String loginUrl = APIURL.LOGIN_URL;
        DataView dataView = new DataView()
        {
            @Override
            public void onGetDataSuccess(ResultItem resultItem, String requestTag)
            {
                if (null != resultItem && resultItem.success())
                {
                    LoginSuccessItem loginSuccessItem = GsonUtils.jsonToClass(resultItem.properties.getAsJsonObject().toString(), LoginSuccessItem.class);
                    if (null != loginSuccessItem)
                    {

                        mActivity.saveLoginSuccessItem(loginSuccessItem);
                        if(mActivity instanceof  LoginActivity)
                        {      mActivity.showToast("自动登录成功");
                            MainActivity.actioMain(mActivity);
                        }
                        else
                        {
                            mActivity.showToast("session过期，自动登录成功");
                        }
                    }
                }
            }

            @Override
            public void onGetDataFailured(String msg, String requestTag)
            {
                mActivity.showToast("自动登录失败，请重新点击登录按钮登录");
                if(!(mActivity instanceof  LoginActivity))
                {
                    LoginActivity.actionLogin(mActivity);
                }
            }
        };
        RequestUtils.getDataFromUrlByPost(mActivity, loginUrl, encryptUsernamePwd, dataView);
    }
}
