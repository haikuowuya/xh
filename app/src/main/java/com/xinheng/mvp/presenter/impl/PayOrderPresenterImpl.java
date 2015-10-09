package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.PostItem;
import com.xinheng.mvp.presenter.PayOrderPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 支付订单接口实现
 */
public class PayOrderPresenterImpl implements PayOrderPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;
    private String mRequestTag;

    public PayOrderPresenterImpl(BaseActivity activity, DataView dataView, String requestTag)
    {
        mActivity = activity;
        mDataView = dataView;
        mRequestTag = requestTag;
    }

    public PayOrderPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    @Override
    public void doPayOrder(String  id)
    {
        String userMedicalUrl = APIURL.PAY_ORDER_URL;
        PostItem postItem = new PostItem();
        postItem.id = id;
      //  item.userId = mActivity.getLoginSuccessItem().id;
        String mingPostBody = GsonUtils.toJson(postItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        System.out.println("postBody = " + postBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, userMedicalUrl, postBody, mActivity.getLoginSuccessItem(), mDataView, mRequestTag);
    }


}
