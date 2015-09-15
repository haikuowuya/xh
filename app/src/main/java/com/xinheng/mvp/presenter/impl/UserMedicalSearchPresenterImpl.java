package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.drug.PostDrugSearchItem;
import com.xinheng.mvp.presenter.UserMedicalSearchPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 用户保存药方接口实现
 */
public class UserMedicalSearchPresenterImpl implements UserMedicalSearchPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;

    public UserMedicalSearchPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    @Override
    public void doAddMedicalSearch(String keyword)
    {
        String userMedicalUrl = APIURL.SEARCH_MEDICAL_URL;
        PostDrugSearchItem postItem = new PostDrugSearchItem();
        postItem.keyword = keyword;
        postItem.userId = mActivity.getLoginSuccessItem().id;
        String mingPostBody = GsonUtils.toJson(postItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        System.out.println("postBody = " + postBody );
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, userMedicalUrl, postBody, mActivity.getLoginSuccessItem(), mDataView);
    }


}
