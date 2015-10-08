package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.address.PostDeleteAddressItem;
import com.xinheng.mvp.presenter.DeletePatientPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 删除常用就诊人
 */
public class DeletePatientPresenterImpl implements DeletePatientPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;
    private  String mRequestTag;

    public DeletePatientPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }
    public DeletePatientPresenterImpl(BaseActivity activity, DataView dataView, String requestTag)
    {
        mActivity = activity;
        mRequestTag = requestTag;
        mDataView = dataView;
    }

    @Override
    public void doDeletePatient(String id)
    {
        //删除收货地址
        String deleteAddressUrl = APIURL.DELETE_PATIENT_URL;
        PostDeleteAddressItem item = new PostDeleteAddressItem();
        item.id = id;
        String mingPostBody = GsonUtils.toJson(item);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, deleteAddressUrl, postBody, mActivity.getLoginSuccessItem(), mDataView,mRequestTag);

    }
}
