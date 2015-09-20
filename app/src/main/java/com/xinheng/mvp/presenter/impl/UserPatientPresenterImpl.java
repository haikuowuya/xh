package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.PostItem;
import com.xinheng.mvp.presenter.UserPatientPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 获取医生详情接口实现
 */
public class UserPatientPresenterImpl implements UserPatientPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;

    public UserPatientPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    @Override
    public void doGetUserPatient()
    {
        String userPatientListUrl = APIURL.GET_USER_PATIENT_LIST_URL;
        PostItem  postItem = new PostItem();
        postItem.userId = mActivity.getLoginSuccessItem().id;
        String mingPostBody = GsonUtils.toJson(postItem );
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, userPatientListUrl, postBody, mActivity.getLoginSuccessItem(), mDataView);
    }


}
