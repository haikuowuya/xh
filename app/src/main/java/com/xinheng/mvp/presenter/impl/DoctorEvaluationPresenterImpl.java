package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.UserDoctorDetailItem;
import com.xinheng.mvp.model.doctor.BasePostDoctorItem;
import com.xinheng.mvp.model.doctor.PostDoctorEvaluationItem;
import com.xinheng.mvp.presenter.DoctorEvaluationPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * Created by Steven on 2015/9/8 0008.
 */
public class DoctorEvaluationPresenterImpl implements DoctorEvaluationPresenter

{
    private DataView mDataView;
    private BaseActivity mActivity;

    public DoctorEvaluationPresenterImpl(BaseActivity activity,DataView dataView)
    {
        mDataView = dataView;
        mActivity = activity;
    }

    @Override
    public void doSubmitDoctorEvaluation(UserDoctorDetailItem userDoctorDetailItem, String serviceDescribe, String effectDescribe, String feeDescribe)
    {
        String doctorEvaluationUrl = APIURL.ADD_USER_DOCTOR_EVALUATION_URL;
        PostDoctorEvaluationItem  postDoctorEvaluationItem = new PostDoctorEvaluationItem();
        postDoctorEvaluationItem.userId = mActivity.getLoginSuccessItem().id;
        postDoctorEvaluationItem.doctId = userDoctorDetailItem.doctId;
        postDoctorEvaluationItem.effectDescribe =effectDescribe;
        postDoctorEvaluationItem.serviceDescribe = serviceDescribe;
        postDoctorEvaluationItem.feeDescribe = feeDescribe;
        postDoctorEvaluationItem.feeGrade = userDoctorDetailItem.feeGrade;
        postDoctorEvaluationItem.effectGrade = userDoctorDetailItem.effectGrade;
        postDoctorEvaluationItem.serviceGrade = userDoctorDetailItem.serviceGrade;
        String mingPostBody = GsonUtils.toJson(postDoctorEvaluationItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, doctorEvaluationUrl,postBody,mActivity.getLoginSuccessItem(), mDataView);
    }

    @Override
    public void doGetDoctorEvaluationDetail(String doctId)
    {
        String doctorEvaluationUrl = APIURL.GET_USER_DOCTOR_EVALUATION_URL;
        BasePostDoctorItem postDoctorItem = new BasePostDoctorItem();
        postDoctorItem.doctId = doctId;
        String mingPostBody = GsonUtils.toJson(postDoctorItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, doctorEvaluationUrl,postBody,mActivity.getLoginSuccessItem(), mDataView);
    }
}
