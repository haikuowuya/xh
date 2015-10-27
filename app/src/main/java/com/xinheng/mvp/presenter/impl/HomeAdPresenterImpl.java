package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.PostItem;
import com.xinheng.mvp.presenter.HomeAdPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 获取首页顶部广告接口实现类
 */
public class HomeAdPresenterImpl implements HomeAdPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;

    private  String mRequestTag;

    public HomeAdPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    public HomeAdPresenterImpl(BaseActivity activity, DataView dataView, String requestTag)
    {
        mActivity = activity;
        mDataView = dataView;
        mRequestTag = requestTag;
    }

    @Override
    public void doGetHomeAd()
    {
        String userPatientListUrl = APIURL.GET_HOME_AD_LIST_URL;
        PostItem postItem = new PostItem();
        postItem.page = 5+"";
        postItem.userId = mActivity.getLoginSuccessItem().id;
        String mingPostBody = GsonUtils.toJson(postItem );
      //  mingPostBody = mingPostBody.replaceAll("DEFAULT","default");
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, userPatientListUrl, postBody, mActivity.getLoginSuccessItem(), mDataView,mRequestTag);
    }


}
