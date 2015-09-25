package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.appointment.PostPatientRecordItem;
import com.xinheng.mvp.presenter.PatientRecordPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * Created by Steven on 2015/9/9 0009.
 */
public class PatientRecordPresenterImpl implements PatientRecordPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;
    private String mRequestTag;

    public PatientRecordPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }
    public PatientRecordPresenterImpl(BaseActivity activity, DataView dataView,String requestTag)
    {
        mActivity = activity;
        mDataView = dataView;
        mRequestTag = requestTag;
    }

    @Override
    public  void  doGetPatientRecord(String patientId, String doctId)
    {
        String patientRecordUrl  = APIURL.GET_PATIENT_RECORD_LIST_URL;
        PostPatientRecordItem postItem = new PostPatientRecordItem();
        postItem.patientId = patientId;
        postItem.doctId = doctId;
        String mingPostBody = GsonUtils.toJson(postItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        System.out.println("postBody = " + postBody );
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, patientRecordUrl, postBody, mActivity.getLoginSuccessItem(), mDataView,mRequestTag);
    }
}
