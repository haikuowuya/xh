package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.PostItem;
import com.xinheng.mvp.presenter.UserDoctorPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * Created by Steven on 2015/9/7 0007.
 */
public class UserDoctorPresenterImpl implements UserDoctorPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;

    public UserDoctorPresenterImpl(BaseActivity baseActivity, DataView dataView)
    {
        mActivity = baseActivity;
        mDataView = dataView;
    }

    @Override
    public void doGetUserDoctor()
    {
        String userDoctorUrl = APIURL.USER_DOCTOR_LIST;
        PostItem postItem = new PostItem();
        postItem.userId = mActivity.getLoginSuccessItem().id;
        String mingPostBody = GsonUtils.toJson(postItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        System.out.println("postBody = " + postBody );
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, userDoctorUrl, postBody, mActivity.getLoginSuccessItem(), mDataView);

    }
}
