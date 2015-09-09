package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.depart.PostDepartDoctorItem;
import com.xinheng.mvp.presenter.DepartmentDoctorPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 获取科室中的医生列表信息实现
 */
public class DepartmentDoctorPresenterImpl implements DepartmentDoctorPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;

    public DepartmentDoctorPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    @Override
    public void doGetDepartDoctorList(String departId)
    {
        String departDoctorUrl = APIURL.GET_DEPARTMENT_DOCTOR_LIST_URL;
        PostDepartDoctorItem postDepartDoctorItem = new PostDepartDoctorItem();
        postDepartDoctorItem.id = departId;
        String mingPostBody = GsonUtils.toJson(postDepartDoctorItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, departDoctorUrl, postBody, mActivity.getLoginSuccessItem(), mDataView);
    }
}
