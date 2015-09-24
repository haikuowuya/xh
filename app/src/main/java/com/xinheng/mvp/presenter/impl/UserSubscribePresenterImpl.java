package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.subscribe.PostUserSubscribeItem;
import com.xinheng.mvp.presenter.UserSubscribePresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * Created by Steven on 2015/9/11 0011.
 */
public class UserSubscribePresenterImpl implements UserSubscribePresenter
{
    private BaseActivity mActivity;

    private DataView mDataView;

    public UserSubscribePresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    @Override
    public void doGetUserSubscribe(String type)
    {
        String userSubscribeUrl = APIURL.USER_SUBSCRIBE_LIST_URL;
        PostUserSubscribeItem postItem = new PostUserSubscribeItem();
        postItem.userId = mActivity.getLoginSuccessItem().id;
        postItem.type = type;
        String mingPostBody = GsonUtils.toJson(postItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        System.out.println("postBody = " + postBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, userSubscribeUrl, postBody, mActivity.getLoginSuccessItem(), mDataView);
    }
}
