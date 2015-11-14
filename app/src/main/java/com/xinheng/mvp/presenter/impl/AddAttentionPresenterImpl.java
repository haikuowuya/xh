package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.PostItem;
import com.xinheng.mvp.model.doctor.BasePostDoctorItem;
import com.xinheng.mvp.presenter.AttentionPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 科室医生-医生详情-添加关注接口的实现
 */
public class AddAttentionPresenterImpl implements AttentionPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;
    private String mRequestTag;

    public AddAttentionPresenterImpl(BaseActivity activity, DataView dataView, String requestTag)
    {
        mActivity = activity;
        mDataView = dataView;
        mRequestTag = requestTag;
    }

    @Override
    public void doAddAttention(String doctId)
    {
        String attentionUrl = APIURL.ADD_ATTENTION_URL;
        BasePostDoctorItem item = new BasePostDoctorItem();
        item.doctId = doctId;
        item.userId = mActivity.getLoginSuccessItem().id;
        String mingPostBody = GsonUtils.toJson(item);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        System.out.println("postBody = " + postBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, attentionUrl, postBody, mActivity.getLoginSuccessItem(), mDataView, mRequestTag);
    }

    @Override
    public void doCancelAttention(String id)
    {
        String attentionUrl = APIURL.CANCEL_ATTENTION_URL;
        PostItem item = new PostItem();
        item.id = id;
        item.userId = mActivity.getLoginSuccessItem().id;
        String mingPostBody = GsonUtils.toJson(item);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        System.out.println("postBody = " + postBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, attentionUrl, postBody, mActivity.getLoginSuccessItem(), mDataView, mRequestTag);

    }

}
