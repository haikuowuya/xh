package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.PostItem;
import com.xinheng.mvp.presenter.ConfirmOrderPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 获取确认订单中数据接口的实现
 */
public class ConfirmOrderPresenterImpl implements ConfirmOrderPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;
    private String mRequestTag;

    public ConfirmOrderPresenterImpl(BaseActivity activity, DataView dataView, String requestTag)
    {
        mActivity = activity;
        mDataView = dataView;
        mRequestTag = requestTag;
    }

    public ConfirmOrderPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    @Override
    public void doGetConfirmOrder(String orderId)
    {
        String userMedicalUrl = APIURL.CONFIRM_ORDER_URL;
        PostItem postItem = new PostItem();
        postItem.id = orderId;
        postItem.userId = mActivity.getLoginSuccessItem().id;
        String mingPostBody = GsonUtils.toJson(postItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        System.out.println("postBody = " + postBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, userMedicalUrl, postBody, mActivity.getLoginSuccessItem(), mDataView, mRequestTag);
    }

}
