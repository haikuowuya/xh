package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.order.PostOrderSearchItem;
import com.xinheng.mvp.presenter.UserOrderSearchPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 我的订单搜索实现类.
 */
public class UserOrderSearchPresenterImpl implements UserOrderSearchPresenter
{

    public BaseActivity mActivity;
    public DataView mDataView;

    public UserOrderSearchPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    @Override
    public void doGetUserOrderSearch(String keyword, String status)
    {
        String useOrderSearchUrl = APIURL.USER_ORDER_SEARCH;
        PostOrderSearchItem postOrderSearchListItem = new PostOrderSearchItem();
        postOrderSearchListItem.userId = mActivity.getLoginSuccessItem().id;
        postOrderSearchListItem.state = status;
        postOrderSearchListItem.keyword = keyword;
        String mingPostBody = GsonUtils.toJson(postOrderSearchListItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, useOrderSearchUrl, postBody, mActivity.getLoginSuccessItem(), mDataView);

    }
}
