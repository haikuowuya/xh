package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.online.PostOnLineItem;
import com.xinheng.mvp.presenter.OnLinePresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * Created by Administrator on 2015/8/29 0029.
 */
public class OnLinePresenterImpl implements OnLinePresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;

    public OnLinePresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    @Override
    public void doGetOnLine()
    {
        String onLineUrl = APIURL.ONLINE_URL;
        PostOnLineItem postOnLineItem = new PostOnLineItem();
        String mingPostBody = GsonUtils.toJson(postOnLineItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String encryptPostBody = RSAUtil.clientEncrypt(mingPostBody);
        System.out.println("加密字符串 = " + encryptPostBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, onLineUrl, encryptPostBody,mActivity.getLoginSuccessItem(),  mDataView);
    }
}
