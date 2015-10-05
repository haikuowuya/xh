package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.common.OkHttpCallbackImpl;
import com.xinheng.http.OkHttpUtils;
import com.xinheng.mvp.model.user.PostMobileItem;
import com.xinheng.mvp.presenter.UserPhotoPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.RSAUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取确认订单中数据接口的实现
 */
public class UserPhotoPresenterImpl implements UserPhotoPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;
    private String mRequestTag;

    public UserPhotoPresenterImpl(BaseActivity activity, DataView dataView, String requestTag)
    {
        mActivity = activity;
        mDataView = dataView;
        mRequestTag = requestTag;
    }

    public UserPhotoPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    @Override
    public void doUploadPhoto(String imageFilePath)
    {
        String uploadUrl = APIURL.USER_UPLOAD_PHOTO;
        String phone = mActivity.getLoginSuccessItem().mobile;
        PostMobileItem postMobileItem = new PostMobileItem();
        postMobileItem.mobile = RSAUtil.clientEncrypt(phone);
        Map<String, String> postMap = new HashMap<>();
        postMap.put("mobile", postMobileItem.mobile);
        OkHttpUtils.customXHAsyncExecuteWithFile(uploadUrl, mActivity.getLoginSuccessItem(), postMap, new File(imageFilePath), new OkHttpCallbackImpl(mActivity, mDataView));

    }
}
