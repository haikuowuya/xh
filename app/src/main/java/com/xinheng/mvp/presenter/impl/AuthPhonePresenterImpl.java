package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.PostItem;
import com.xinheng.mvp.presenter.AuthPhonePresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 作者： libo
 * 日期： 2015/8/24 0024
 * 时间： 16:34
 * 说明：修改密码 验证手机号码 接口的实现类
 */
public class AuthPhonePresenterImpl implements AuthPhonePresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;
    private String mRequestTag;

    public AuthPhonePresenterImpl(BaseActivity activity, DataView dataView, String requestTag)
    {

        mActivity = activity;
        mDataView = dataView;
        mRequestTag = requestTag;
    }

    @Override
    public  void  doAuthPhone(String phone,String code)
    {
        String departDoctorUrl = APIURL.AUTH_PHONE_WITH_CODE_URL;
        PostPhoneCodeItem item = new PostPhoneCodeItem();
        item.mobile = phone;
        item.checkcode = code;
        String mingPostBody = GsonUtils.toJson(item);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, departDoctorUrl, postBody, mActivity.getLoginSuccessItem(), mDataView, mRequestTag);
    }

    public static  class PostPhoneCodeItem extends PostItem
    {
        public String mobile;//手机号码
        public String checkcode;//验证码

    }
}
