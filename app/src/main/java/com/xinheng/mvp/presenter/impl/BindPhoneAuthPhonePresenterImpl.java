package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.PostItem;
import com.xinheng.mvp.presenter.BindPhoneAuthPhonePresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 作者： libo
 * 日期： 2015/8/24 0024
 * 时间： 16:34
 * 说明：绑定手机号码验证 验证手机号码   接口的实现类
 */
public class BindPhoneAuthPhonePresenterImpl implements BindPhoneAuthPhonePresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;
    private String mRequestTag;

    public BindPhoneAuthPhonePresenterImpl(BaseActivity activity, DataView dataView, String requestTag)
    {
        mActivity = activity;
        mDataView = dataView;
        mRequestTag = requestTag;
    }

    @Override
    public  void  doAuthOldPhone(String phone,String code)
    {
        String departDoctorUrl = APIURL.BIND_PHONE_AUTH_OLD_PHONE_WITH_CODE_URL;
        PostOldPhoneWithCodeItem item = new PostOldPhoneWithCodeItem();
        item.mobile = phone;
        item.checkcode = code;
        String mingPostBody = GsonUtils.toJson(item);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, departDoctorUrl, postBody, mActivity.getLoginSuccessItem(), mDataView, mRequestTag);
    }
 @Override
    public void  doAuthNewPhone(String oldPhone , String newPhone , String newPhoneCode)
    {
        String departDoctorUrl = APIURL.BIND_PHONE_AUTH_NEW_PHONE_WITH_CODE_URL;
        PostNewPhoneWithCodeItem item = new PostNewPhoneWithCodeItem();
        item.mobile = oldPhone;
        item.newmobile = newPhone;
        item.checkcode = newPhoneCode;
        String mingPostBody = GsonUtils.toJson(item);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, departDoctorUrl, postBody, mActivity.getLoginSuccessItem(), mDataView, mRequestTag);
    }

    public static  class PostOldPhoneWithCodeItem extends PostItem
    {
        public String mobile;//手机号码
        public String checkcode;//验证码
    }

    public static  class PostNewPhoneWithCodeItem extends PostItem
    {
        public String mobile;//原手机号码
        public String newmobile;//新的手机号码
        public String checkcode;//验证码
    }
}
