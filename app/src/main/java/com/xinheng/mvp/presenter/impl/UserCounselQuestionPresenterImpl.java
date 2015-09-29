package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.user.PostUserCounselItem;
import com.xinheng.mvp.presenter.UserCounselQuestionPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 我的咨询 -咨询详情 -追问接口的实现
 */
public class UserCounselQuestionPresenterImpl implements UserCounselQuestionPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;

    private String mRequestTag;

    public UserCounselQuestionPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    public UserCounselQuestionPresenterImpl(BaseActivity activity, DataView dataView, String requestTag)
    {
        mActivity = activity;
        mDataView = dataView;
        mRequestTag = requestTag;
    }

    @Override
    public void doUserCounselQuestion(PostUserCounselItem item)
    {
        String userCounselUrl = APIURL.USER_COUNSEL_QUESTION_URL;
        String mingPostBody = GsonUtils.toJson(item);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        System.out.println("postBody = " + postBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, userCounselUrl, postBody, mActivity.getLoginSuccessItem(), mDataView, mRequestTag);
    }
}
