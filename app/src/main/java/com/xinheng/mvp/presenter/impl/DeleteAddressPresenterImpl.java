package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.address.PostDeleteAddressItem;
import com.xinheng.mvp.presenter.DeleteAddressPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * Created by Administrator on 2015/9/20 0020.
 */
public class DeleteAddressPresenterImpl implements DeleteAddressPresenter
{
    private BaseActivity mActivity;

    private DataView mDataView;

    public DeleteAddressPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    @Override
    public void doDeleteAddress(String id)
    {
        //删除收货地址
        String userOrderUrl = APIURL.DELETE_ADDRESS_URL;
        PostDeleteAddressItem item = new PostDeleteAddressItem();
        item.id = id;
        String mingPostBody = GsonUtils.toJson(item);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, userOrderUrl, postBody, mActivity.getLoginSuccessItem(), mDataView);

    }
}
