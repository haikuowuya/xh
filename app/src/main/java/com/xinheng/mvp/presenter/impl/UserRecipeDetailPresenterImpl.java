package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.PostItem;
import com.xinheng.mvp.presenter.UserRecipeDetailPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 我的处方详情实现
 */
public class UserRecipeDetailPresenterImpl implements UserRecipeDetailPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;
    private String mRequestTag;

    public UserRecipeDetailPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    public UserRecipeDetailPresenterImpl(BaseActivity activity, DataView dataView, String requestTag)
    {
        mActivity = activity;
        mRequestTag = requestTag;
        mDataView = dataView;
    }

    @Override
    public void doGetUserRecipeDetail(String id)
    {
        String userCounselUrl = APIURL.GET_USER_RECIPE_DETAIL_URL;
        PostItem postItem = new PostItem();
        postItem.userId = mActivity.getLoginSuccessItem().id;
        postItem.id = id;
        String mingPostBody = GsonUtils.toJson(postItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        System.out.println("postBody = " + postBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, userCounselUrl, postBody, mActivity.getLoginSuccessItem(), mDataView, mRequestTag);
    }

}
