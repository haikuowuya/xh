package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.PostItem;
import com.xinheng.mvp.model.appointment.PostUserAppointmentItem;
import com.xinheng.mvp.presenter.UserAppointmentDetailPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 我的预约详情接口实现类
 */
public class UserAppointmentDetailPresenterImpl implements UserAppointmentDetailPresenter
{
    private BaseActivity mActivity;

    private DataView mDataView;
    private  String mRequestTag;

    public UserAppointmentDetailPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    public UserAppointmentDetailPresenterImpl(BaseActivity activity, DataView dataView ,String requestTag)
    {
        mActivity = activity;
        mDataView = dataView;
        this.mRequestTag = requestTag;
    }
    @Override
    public void doGetUserAppointmentDetail(String userAppointmentId)
    {
        String userSubscribeUrl = APIURL.USER_APPOINTMENT_DETAIL_URL;
        PostItem postItem = new PostUserAppointmentItem();
        postItem.userId = mActivity.getLoginSuccessItem().id;
        postItem.id = userAppointmentId;
        String mingPostBody = GsonUtils.toJson(postItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        System.out.println("postBody = " + postBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, userSubscribeUrl, postBody, mActivity.getLoginSuccessItem(), mDataView,mRequestTag);
    }


}
