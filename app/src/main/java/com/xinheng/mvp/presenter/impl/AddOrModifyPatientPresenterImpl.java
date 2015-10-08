package com.xinheng.mvp.presenter.impl;

import android.text.TextUtils;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.patient.PostAddPatientItem;
import com.xinheng.mvp.presenter.AddOrModifyPatientPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 添加就诊人信息
 */
public class AddOrModifyPatientPresenterImpl implements AddOrModifyPatientPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;

    public AddOrModifyPatientPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    @Override
    public void doAddUserPatient(PostAddPatientItem item)
    {
        String userPatientListUrl = APIURL.ADD_USER_PATIENT_URL;
        if(!TextUtils.isEmpty(item.id)    )//如果就诊人id不为空，表示是修改
        {
            userPatientListUrl = APIURL.MODIFY_PATIENT_URL;
        }
        item.userId = mActivity.getLoginSuccessItem().id;
        String mingPostBody = GsonUtils.toJson(item );
      //  mingPostBody = mingPostBody.replaceAll("DEFAULT","default");
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, userPatientListUrl, postBody, mActivity.getLoginSuccessItem(), mDataView);
    }

}
