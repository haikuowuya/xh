package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.PostItem;
import com.xinheng.mvp.presenter.UserRecipePresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 *  获取我的处方列表的实现类
 */
public class UserRecipePresenterImpl implements UserRecipePresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;

    public UserRecipePresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }
    @Override
    public void doGetUserRecipe()
    {
        String userMedicalUrl = APIURL.USER_RECIPE_LIST_URL;
        PostItem postItem = new PostItem();
        postItem.userId = mActivity.getLoginSuccessItem().id;
        String mingPostBody = GsonUtils.toJson(postItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        System.out.println("postBody = " + postBody );
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, userMedicalUrl, postBody, mActivity.getLoginSuccessItem(), mDataView);

    }


}
