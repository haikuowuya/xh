package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.BaseEmptyItem;
import com.xinheng.mvp.presenter.SendCodePresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.MD5;
import com.xinheng.util.RSAUtil;

/**
 * 校验手机号码和对应验证码的实现类
 */
public class SendCodePresenterImpl implements SendCodePresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;

    private String mRequestTag;

    public SendCodePresenterImpl(BaseActivity activity, DataView dataView, String requestTag)
    {

        mActivity = activity;
        mDataView = dataView;
        mRequestTag = requestTag;

    }

    @Override
    public void doSendCode(String phone)
    {
        String verifyUrl = APIURL.SEND_SMS_CODE_URL;
        String mingPostBody = null;
        PostSendCodeItem postSendCodeItem = new PostSendCodeItem();
        postSendCodeItem.mobile = phone;
        String Str1 = new StringBuffer(phone).reverse().toString() + PostSendCodeItem.UA;
        String Str2 = new MD5().getMD5_32(Str1) + PostSendCodeItem.UA;
        String key = new MD5().getMD5_32(Str2);
        postSendCodeItem.key = key;
        mingPostBody = GsonUtils.toJson(postSendCodeItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, verifyUrl, postBody, mActivity.getLoginSuccessItem(), mDataView, mRequestTag);
    }

    public static class PostSendCodeItem extends BaseEmptyItem
    {
        public static final String UA = "ua";
        public static final String SALT = "wWw.XhkJ.C0m";
        public String mobile;//手机号码
        public String key;//验证key 策略：MD5(MD5(mobile(倒序)+ua)+salt)
        //  public String salt="wWw.XhkJ.C0m"
    }
}
