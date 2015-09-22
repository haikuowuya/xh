package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.depart.PostDoctorDetailItem;
import com.xinheng.mvp.presenter.DoctorDetailPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 获取医生详情接口实现
 */
public class DoctorDetailPresenterImpl implements DoctorDetailPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;

    private String mRequestTag;


    public DoctorDetailPresenterImpl(BaseActivity activity, DataView dataView,String requestTag)
    {
        mActivity = activity;
        mDataView = dataView;
        mRequestTag = requestTag;
    }

    @Override
    public void doGetDoctorDetail(String did)
    {
        String departDoctorUrl = APIURL.GET_DOCTOR_DETAIL_URL;
        PostDoctorDetailItem postDepartDoctorItem = new PostDoctorDetailItem();
        postDepartDoctorItem.id = did;
        String mingPostBody = GsonUtils.toJson(postDepartDoctorItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, departDoctorUrl, postBody, mActivity.getLoginSuccessItem(), mDataView,mRequestTag);
    }


}
