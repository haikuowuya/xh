package com.xinheng.mvp.presenter.impl;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.OkHttpUtils;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.prescription.PostSavePrescriptionItem;
import com.xinheng.mvp.presenter.SavePrescriptionPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

import java.io.File;
import java.io.IOException;

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
        System.out.println("postBody = " + postBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, userMedicalUrl, postBody, mActivity.getLoginSuccessItem(), mDataView);

        Callback callback= new Callback()
        {
            @Override
            public void onFailure(Request request, IOException e)
            {
                System.out.println("e = " + e.toString() );
            }

            @Override
            public void onResponse(Response response) throws IOException
            {
                System.out.println("response = " + response.toString() );
            }
        };
        OkHttpUtils.customXHasyncExecuteWithFile(userMedicalUrl,mActivity.getLoginSuccessItem(),postBody,new File("/mnt/sdcard/tmp.jpg"),callback);
    }


}
