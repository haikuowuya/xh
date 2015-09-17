package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.BaseEmptyItem;
import com.xinheng.mvp.presenter.PhoneVerifyPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 校验手机号码和对应验证码的实现类
 */
public class PhoneVerifyPresenterImpl implements PhoneVerifyPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;
    private int mType;

    public PhoneVerifyPresenterImpl(BaseActivity activity, int type, DataView dataView)
    {

        mActivity = activity;
        mDataView = dataView;
        this.mType = type;
    }

    @Override
    public void doVerifyPhone(String phone, String checkCode)
    {
        String verifyUrl = APIURL.VERIFY_MODIFY_BIND_PHONE_URL;
        String mingPostBody = null;
        if (mType == TYPE_OLD_PHONE)
        {
            PostPhoneVerifyItem item = new PostPhoneVerifyItem();
            item.checkcode = checkCode;
            item.mobile = phone;
            mingPostBody = GsonUtils.toJson(item);
        }
        else if (mType == TYPE_NEW_PHONE)
        {
            PostNewPhoneVerifyItem postNewPhoneVerifyItem = new PostNewPhoneVerifyItem();
            postNewPhoneVerifyItem.newmobile = phone;
            postNewPhoneVerifyItem.checkcode = checkCode;
            mingPostBody = GsonUtils.toJson(postNewPhoneVerifyItem);
        }
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity,verifyUrl,postBody, mActivity.getLoginSuccessItem(), mDataView);
    }

    public static class PostPhoneVerifyItem extends BaseEmptyItem
    {
        public String mobile;//手机号码
        public String checkcode;//验证码
    }

    public static class PostNewPhoneVerifyItem extends BaseEmptyItem
    {
        public String newmobile;//新的手机号码
        public String checkcode;//验证码
    }
}
