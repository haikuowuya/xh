package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.PostItem;
import com.xinheng.mvp.presenter.UserMedicalPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * Created by Steven on 2015/9/9 0009.
 */
public class UserMedicalPresenterImpl implements UserMedicalPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;

    public UserMedicalPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    @Override
    public void doGetUserMedical()
    {
        String userMedicalUrl = APIURL.USER_MEDICAL_LIST_URL;
        PostItem postItem = new PostItem();
        postItem.userId = mActivity.getLoginSuccessItem().id;
        String mingPostBody = GsonUtils.toJson(postItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        System.out.println("postBody = " + postBody );
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, userMedicalUrl, postBody, mActivity.getLoginSuccessItem(), mDataView);
    }
}
