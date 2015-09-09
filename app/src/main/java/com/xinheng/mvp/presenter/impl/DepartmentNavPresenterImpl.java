package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.presenter.DepartmentNavPresenter;
import com.xinheng.mvp.view.DataView;

/**
 * Created by Steven on 2015/9/9 0009.
 */
public class DepartmentNavPresenterImpl implements DepartmentNavPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;

    public DepartmentNavPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    @Override
    public void doGetDepartmentNav()
    {
        String departmentNavUrl = APIURL.GET_DEPARTMENT_NAV_URL;
        String postBody = "111";
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, departmentNavUrl, postBody, mActivity.getLoginSuccessItem(), mDataView);

    }
}
