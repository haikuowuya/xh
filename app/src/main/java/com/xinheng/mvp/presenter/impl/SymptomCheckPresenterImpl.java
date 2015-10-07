package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.PostItem;
import com.xinheng.mvp.model.check.PostGetSymptomResultItem;
import com.xinheng.mvp.presenter.SymptomCheckPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 症状自测结果获取接口实现
 */
public class SymptomCheckPresenterImpl implements SymptomCheckPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;
    private String mRequestTag;

    public SymptomCheckPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    public SymptomCheckPresenterImpl(BaseActivity activity, DataView dataView, String requestTag)
    {
        mActivity = activity;
        mRequestTag = requestTag;
        mDataView = dataView;
    }

    @Override
    public void doGetSymptomCheckQuestion(String symptomId)
    {
        String userCounselUrl = APIURL.GET_SYMPTOM_CHECK_FIRST_URL;
        PostItem postItem = new PostItem();
        postItem.userId = mActivity.getLoginSuccessItem().id;
        postItem.id = symptomId;
        String mingPostBody = GsonUtils.toJson(postItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        System.out.println("postBody = " + postBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, userCounselUrl, postBody, mActivity.getLoginSuccessItem(), mDataView, mRequestTag);
    }

    @Override
    public void doGetSymptomCheckResultOrNext(String flowId, String answer)
    {
        String userCounselUrl = APIURL.GET_SYMPTOM_CHECK_NEXT_URL;
        PostGetSymptomResultItem postItem = new PostGetSymptomResultItem();
        postItem.flowId = flowId;
        postItem.answer = answer;
        String mingPostBody = GsonUtils.toJson(postItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        System.out.println("postBody = " + postBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, userCounselUrl, postBody, mActivity.getLoginSuccessItem(), mDataView, mRequestTag);
    }

}
