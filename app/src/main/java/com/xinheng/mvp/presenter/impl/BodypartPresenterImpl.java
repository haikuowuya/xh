package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.presenter.BodypartPresenter;
import com.xinheng.mvp.view.DataView;

/**
 * 智能导诊-获取身体部位列表接口的实现类
 */
public class BodypartPresenterImpl implements BodypartPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;

    public BodypartPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    @Override
    public void doGetBodypartList()
    {
        String bodyPartListUrl = APIURL.GET_BODY_PART_LIST_URL;
        String postBody = "1";
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, bodyPartListUrl, postBody, mActivity.getLoginSuccessItem(), mDataView);

    }
}
