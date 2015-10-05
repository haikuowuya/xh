package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.check.PostGetCheckListItem;
import com.xinheng.mvp.presenter.DepartCheckPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.Constants;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 预约检查-获取科室检查项目接口的实现
 */
public class DepartCheckPresenterImpl implements DepartCheckPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;
    private String mRequestTag;

    private boolean mShowProgressDialog ;

    public DepartCheckPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }
    public DepartCheckPresenterImpl(BaseActivity activity, DataView dataView, String requestTag)
    {
        mActivity = activity;
        mDataView = dataView;
        mRequestTag = requestTag;
    }

    public DepartCheckPresenterImpl(BaseActivity activity, DataView dataView, String requestTag, boolean showProgressDialog)
    {
        mActivity = activity;
        mDataView = dataView;
        this.mShowProgressDialog = showProgressDialog;
        mRequestTag = requestTag;
    }
    @Override
    public void doGetDepartCheckList()
    {
        String patientRecordUrl  = APIURL.GET_DEPART_CHECK_LIST_URL;
        PostGetCheckListItem postGetCheckListItem = new PostGetCheckListItem();
        postGetCheckListItem.hid = Constants.HID;
        String mingPostBody = GsonUtils.toJson(postGetCheckListItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, patientRecordUrl, postBody, mActivity.getLoginSuccessItem(), mDataView,mRequestTag ,mShowProgressDialog);
    }


}
