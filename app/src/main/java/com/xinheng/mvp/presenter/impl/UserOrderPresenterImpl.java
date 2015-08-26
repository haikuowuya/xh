package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.PostListItem;
import com.xinheng.mvp.presenter.UserOrderPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/26 0026
 * 时间： 17:13
 * 说明： 获取我的订单的实现类
 */
public class UserOrderPresenterImpl implements UserOrderPresenter
{
    private BaseActivity mActivity;

    private DataView mDataView;

    public UserOrderPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    @Override
    public void doGetUserOrder(String status)
    {
        String userOrderUrl = APIURL.USER_ORDER_LIST;
        PostListItem postListItem = new PostListItem();
        postListItem.userId = mActivity.getLoginSuccessItem().id;
        postListItem.page = "-1";
        String mingPostBody = GsonUtils.toJson(postListItem);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, userOrderUrl,postBody,mActivity.getLoginSuccessItem(),mDataView);
    }
}
