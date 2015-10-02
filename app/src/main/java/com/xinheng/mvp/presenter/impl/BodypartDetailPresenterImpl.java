package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.auto.PostBodypartItem;
import com.xinheng.mvp.presenter.BodypartDetailPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 智能导诊-获取症状列表接口的实现类
 */
public class BodypartDetailPresenterImpl implements BodypartDetailPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;
    private  String mRequestTag;

    public BodypartDetailPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    public BodypartDetailPresenterImpl(BaseActivity activity, DataView dataView,String requestTag)
    {
        mActivity = activity;
        mRequestTag = requestTag;
        mDataView = dataView;
    }
    @Override
    public void doGetBodypartDetailList(String bodypart)
    {
        String bodyPartListUrl = APIURL.GET_BODY_PART_DETAIL_LIST_URL;
        PostBodypartItem  postBodypartItem = new PostBodypartItem();
        postBodypartItem.bodypart = bodypart;
        String mingPostBody = GsonUtils.toJson(postBodypartItem);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, bodyPartListUrl, postBody, mActivity.getLoginSuccessItem(), mDataView,mRequestTag);

    }
}
