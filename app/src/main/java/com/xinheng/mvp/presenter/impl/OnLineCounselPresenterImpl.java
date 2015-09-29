package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.common.OkHttpCallbackImpl;
import com.xinheng.http.OkHttpUtils;
import com.xinheng.mvp.model.doctor.PostOnlineCounselItem;
import com.xinheng.mvp.presenter.OnLineCounselPresenter;
import com.xinheng.mvp.view.DataView;

import java.util.HashMap;
import java.util.Map;

/**
 * 提交预约加号的接口实现
 */
public class OnLineCounselPresenterImpl implements OnLineCounselPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;

    private String mRequestTag;

    public OnLineCounselPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    public OnLineCounselPresenterImpl(BaseActivity activity, DataView dataView, String requestTag)
    {
        mActivity = activity;
        mDataView = dataView;
        mRequestTag = requestTag;
    }

    @Override
    public void doOnLineCounsel(PostOnlineCounselItem item)
    {
        String onlineCounselUrl = APIURL.ONLINE_COUNSEL_URL;
        Map<String, String> postMap = new HashMap<>();
        postMap.put("userId", item.userId);
        postMap.put("age", item.age);
        postMap.put("sex", item.sex);
        postMap.put("mobile", item.mobile);
        postMap.put("checkcode", item.checkcode);
        postMap.put("message", item.message);
        postMap.put("doctId", item.doctId);
        postMap.put("pathema", item.pathema);
        postMap.put("desc", item.desc);
        mActivity.showProgressDialog();
        OkHttpUtils.customXHasyncExecuteWithFile(onlineCounselUrl, mActivity.getLoginSuccessItem(), postMap, item.files, new OkHttpCallbackImpl(mActivity, mDataView));
    }
}
