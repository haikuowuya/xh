package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.check.PostAddCheckItem;
import com.xinheng.mvp.presenter.SubmitAddCheckPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 添加检查接口实现
 */
public class SubmitAddCheckPresenterImpl implements SubmitAddCheckPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;
    private String mRequestTag;

    public SubmitAddCheckPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    public SubmitAddCheckPresenterImpl(BaseActivity activity, DataView dataView,String requestTag)
    {
        mActivity = activity;
        mRequestTag = requestTag;
        mDataView = dataView;
    }


    @Override
    public void doAddCheck(PostAddCheckItem item)
    {
        String departDoctorUrl = APIURL.ADD_CHECK_URL;
        String mingPostBody = GsonUtils.toJson(item);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, departDoctorUrl, postBody, mActivity.getLoginSuccessItem(), mDataView,mRequestTag);
    }
}
