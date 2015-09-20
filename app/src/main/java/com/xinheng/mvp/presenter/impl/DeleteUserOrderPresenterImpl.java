package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.order.PostDeleteOrderItem;
import com.xinheng.mvp.presenter.DeleteUserOrderPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/26 0026
 * 时间： 17:13
 * 说明： 删除我的订单的实现类
 */
public class DeleteUserOrderPresenterImpl implements DeleteUserOrderPresenter
{
    private BaseActivity mActivity;

    private DataView mDataView;

    public DeleteUserOrderPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    @Override
    public void doDeleteUserOrder(String orderId)
    {
        //删除我的订单URL
        String userOrderUrl = APIURL.DELETE_USER_ORDER_URL;

        PostDeleteOrderItem postDeleteOrderItem = new PostDeleteOrderItem();
        postDeleteOrderItem.orderId = orderId;
        String mingPostBody = GsonUtils.toJson(postDeleteOrderItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, userOrderUrl, postBody, mActivity.getLoginSuccessItem(), mDataView);
    }
}
