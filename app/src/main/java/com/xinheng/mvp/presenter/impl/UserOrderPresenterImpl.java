package com.xinheng.mvp.presenter.impl;

import android.text.TextUtils;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.PostListItem;
import com.xinheng.mvp.model.UserOrderItem;
import com.xinheng.mvp.model.order.PostOrderListItem;
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
        //获取我的订单URL
        String userOrderUrl = APIURL.USER_ORDER_LIST;
        String mingPostBody = null;
        if (!TextUtils.isEmpty(status))   //订单状态
        {
            if (status.equals(UserOrderItem.ORDER_STATUS_ALL))
            {
                PostListItem postListItem = new PostListItem();
                postListItem.userId = mActivity.getLoginSuccessItem().id;
                postListItem.page = "-1";
                mingPostBody = GsonUtils.toJson(postListItem);
            }
            else
            {
                PostOrderListItem orderListItem = new PostOrderListItem();
                orderListItem.userId = mActivity.getLoginSuccessItem().id;
                orderListItem.page = "-1";
                orderListItem.state = status;
                mingPostBody = GsonUtils.toJson(orderListItem);
            }
        }
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, userOrderUrl, postBody, mActivity.getLoginSuccessItem(), mDataView);
    }
}
