package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.PostItem;
import com.xinheng.mvp.presenter.UserOrderDetailPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 我的订单详情接口实现
 */
public class UserOrderDetailPresenterImpl implements UserOrderDetailPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;
    private String mRequestTag;

    public UserOrderDetailPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    public UserOrderDetailPresenterImpl(BaseActivity activity, DataView dataView, String requestTag)
    {
        mActivity = activity;
        mRequestTag = requestTag;
        mDataView = dataView;
    }

    @Override
    public void doGetUserOrderDetail(String id)
    {
        String userCounselUrl = APIURL.GET_USER_ORDER_DETAIL_URL;
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
