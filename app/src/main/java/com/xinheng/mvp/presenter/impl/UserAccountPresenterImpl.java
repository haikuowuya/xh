package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.PostItem;
import com.xinheng.mvp.presenter.UserAccountPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 作者： libo
 * 日期： 2015/8/24 0024
 * 时间： 16:34
 * 说明：根据用户id获取账户信息接口实现类
 */
public class UserAccountPresenterImpl implements UserAccountPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;
    private  String mRequestTag;

    public UserAccountPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }
    public UserAccountPresenterImpl(BaseActivity activity, DataView dataView,String requestTag)
    {
        mActivity = activity;
        mRequestTag = requestTag;
        mDataView = dataView;
    }

    @Override
    public void doGetUserAccount()
    {
        String getUserAccountUrl  = APIURL.GET_USER_ACCOUNT_DETAIL_URL;
        PostItem postItem = new PostItem();
        postItem.userId = mActivity.getLoginSuccessItem().id;
        String mingPostBody = GsonUtils.toJson(postItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, getUserAccountUrl, postBody, mActivity.getLoginSuccessItem(), mDataView,mRequestTag);
    }
}
