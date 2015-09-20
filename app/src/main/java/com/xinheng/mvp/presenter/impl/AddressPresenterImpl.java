package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.PostItem;
import com.xinheng.mvp.presenter.AddressPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 收货地址列表实现类
 */
public class AddressPresenterImpl implements AddressPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;

    public AddressPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    @Override
    public void doGetAddressList()
    {
        String userPatientListUrl = APIURL.GET_ADDRESS_LIST_URL;
        PostItem postItem = new PostItem();
        postItem.userId = mActivity.getLoginSuccessItem().id;
        String mingPostBody = GsonUtils.toJson(postItem );
      //  mingPostBody = mingPostBody.replaceAll("DEFAULT","default");
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, userPatientListUrl, postBody, mActivity.getLoginSuccessItem(), mDataView);
    }


}
