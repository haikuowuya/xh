package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.prescription.PostSavePrescriptionItem;
import com.xinheng.mvp.presenter.SavePrescriptionPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 用户保存药方接口实现
 */
public class SavePrescriptionPresenterImpl implements SavePrescriptionPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;

    public SavePrescriptionPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    @Override
    public void doSavePrescription(PostSavePrescriptionItem item )
    {
        String userMedicalUrl = APIURL.SAVE_PRESCRIPTION_URL;
        item.userId = mActivity.getLoginSuccessItem().id;
        String mingPostBody = GsonUtils.toJson(item);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        System.out.println("postBody = " + postBody );
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, userMedicalUrl, postBody, mActivity.getLoginSuccessItem(), mDataView);
    }


}
