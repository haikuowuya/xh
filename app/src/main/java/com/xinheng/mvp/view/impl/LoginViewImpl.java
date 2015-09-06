package com.xinheng.mvp.view.impl;

import com.xinheng.MainActivity;
import com.xinheng.base.BaseActivity;
import com.xinheng.mvp.model.LoginSuccessItem;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;

/**
 * Created by Administrator on 2015/9/6 0006.
 */
public class LoginViewImpl implements DataView
{
    private BaseActivity mActivity;

    public LoginViewImpl(BaseActivity activity)
    {
        mActivity = activity;
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem)
    {
        if (null != resultItem)
        {
            mActivity.showToast(resultItem.message);
            if(resultItem.success())
            {
                LoginSuccessItem loginSuccessItem = GsonUtils.jsonToClass(resultItem.properties.getAsJsonObject().toString(), LoginSuccessItem.class);
                System.out.println("loginSuccessItem = " + loginSuccessItem);
                if (null != loginSuccessItem)
                {
                    mActivity.saveLoginSuccessItem(loginSuccessItem);
                    MainActivity.actioMain(mActivity);
                }
            }
        }
    }

    @Override
    public void onGetDataFailured(String msg)
    {
        mActivity.showCroutonToast(msg);
    }
}
