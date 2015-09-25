package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.appointment.PostUserAppointmentItem;
import com.xinheng.mvp.presenter.UserAppointmentPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 我的预约列表接口实现类
 */
public class UserAppointmentPresenterImpl implements UserAppointmentPresenter
{
    private BaseActivity mActivity;

    private DataView mDataView;

    public UserAppointmentPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    @Override
    public void doGetUserAppointment(String type)
    {
        String userSubscribeUrl = APIURL.USER_SUBSCRIBE_LIST_URL;
        PostUserAppointmentItem postItem = new PostUserAppointmentItem();
        postItem.userId = mActivity.getLoginSuccessItem().id;
        postItem.type = type;
        String mingPostBody = GsonUtils.toJson(postItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        System.out.println("postBody = " + postBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, userSubscribeUrl, postBody, mActivity.getLoginSuccessItem(), mDataView);
    }
}
