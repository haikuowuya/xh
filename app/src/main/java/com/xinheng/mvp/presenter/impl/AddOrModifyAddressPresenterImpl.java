package com.xinheng.mvp.presenter.impl;

import android.text.TextUtils;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.address.PostAddressItem;
import com.xinheng.mvp.presenter.AddOrModifyAddressPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 添加就诊人信息
 */
public class AddOrModifyAddressPresenterImpl implements AddOrModifyAddressPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;

    public AddOrModifyAddressPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    @Override
    public void doAddOrModifyAddress(PostAddressItem item)
    {
        String addressUrl = APIURL.ADD_ADDRESS_URL;
        if(!TextUtils.isEmpty(item.id)    )//如果收货地址id不为空，表示是修改收货地址
        {
            addressUrl = APIURL.MODIFY_ADDRESS_URL;
        }
        item.userId = mActivity.getLoginSuccessItem().id;
        String mingPostBody = GsonUtils.toJson(item );
      //  mingPostBody = mingPostBody.replaceAll("DEFAULT","default");
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, addressUrl, postBody, mActivity.getLoginSuccessItem(), mDataView);
    }


}
