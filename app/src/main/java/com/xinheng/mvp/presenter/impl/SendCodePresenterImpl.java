package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.BaseEmptyItem;
import com.xinheng.mvp.presenter.SendCodePresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 校验手机号码和对应验证码的实现类
 */
public class SendCodePresenterImpl implements SendCodePresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;


    public SendCodePresenterImpl(BaseActivity activity,  DataView dataView)
    {

        mActivity = activity;
        mDataView = dataView;

    }

    @Override
    public void doSendCode(String phone)
    {
        String verifyUrl = APIURL.SEND_SMS_CODE_URL;
        String mingPostBody = null;
        PostSendCodeItem  postSendCodeItem = new PostSendCodeItem();
        postSendCodeItem.mobile = phone;
        postSendCodeItem.key = null;
        mingPostBody = GsonUtils.toJson(postSendCodeItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity,verifyUrl,postBody, mActivity.getLoginSuccessItem(), mDataView);
    }



    public static class PostSendCodeItem extends BaseEmptyItem
   {

       public String   mobile;//手机号码
       public String  key;//验证key 策略：MD5(MD5(mobile(倒序)+ua)+salt)
     //  public String salt="wWw.XhkJ.C0m"
   }
}
