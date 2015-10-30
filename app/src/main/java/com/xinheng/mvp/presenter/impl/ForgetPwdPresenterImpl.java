package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.BaseEmptyItem;
import com.xinheng.mvp.presenter.ForgetPwdPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.MD5;
import com.xinheng.util.RSAUtil;

/**
 * 忘记密码接口的实现
 */
public class ForgetPwdPresenterImpl implements ForgetPwdPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;

    private String mRequestTag;

    public ForgetPwdPresenterImpl(BaseActivity activity, DataView dataView, String requestTag)
    {
        mActivity = activity;
        mDataView = dataView;
        mRequestTag = requestTag;
    }
    public ForgetPwdPresenterImpl(BaseActivity activity, DataView dataView )
    {
        mActivity = activity;
        mDataView = dataView;

    }

    @Override
    public void doAuthAccount(String account)
    {
        String authAccountUrl = APIURL.FORGET_PWD_AUTH_ACCOUNT;
        PostAccountItem postAccountItem = new PostAccountItem();
        postAccountItem.account = account;
        String mingPostBody = GsonUtils.toJson(postAccountItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        System.out.println("postBody = " + postBody );
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, authAccountUrl, postBody, mActivity.getLoginSuccessItem(), mDataView,mRequestTag);

    }

    @Override
    public void doAuthPhone(String phone, String code)
    {
        String authPhoneUrl = APIURL.FORGET_PWD_AUTH_PHONE;
        PostPhoneItem postPhoneItem = new PostPhoneItem();
        postPhoneItem.account = phone;
        postPhoneItem.checkcode = code;
        String mingPostBody = GsonUtils.toJson(postPhoneItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        System.out.println("postBody = " + postBody );
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, authPhoneUrl, postBody, mActivity.getLoginSuccessItem(), mDataView,mRequestTag);
    }

    @Override
    public void doAuthPatient(String account, String patient)
    {
        String authPatientUrl  = APIURL.FORGET_PWD_AUTH_PATIENT;
        PostPatientItem postPatientItem = new PostPatientItem();
        postPatientItem.account = account ;
        postPatientItem.patient = patient;
        String mingPostBody = GsonUtils.toJson(postPatientItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        System.out.println("postBody = " + postBody );
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, authPatientUrl, postBody, mActivity.getLoginSuccessItem(), mDataView,mRequestTag);
    }

    @Override
    public void doResetPwd(String account, String newPwd)
    {
        String resetPwdUrl   = APIURL.FORGET_PWD_RESET_PWD;
        PostResetPwdItem postResetPwdItem = new PostResetPwdItem();
        postResetPwdItem.account = account ;
        postResetPwdItem.newpassword = new MD5().getMD5_32(newPwd);
        String mingPostBody = GsonUtils.toJson(postResetPwdItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        System.out.println("postBody = " + postBody );
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, resetPwdUrl, postBody, mActivity.getLoginSuccessItem(), mDataView,mRequestTag);
    }

    /**
     * 验证帐号需要post提交的实体类
     */
    public static class PostAccountItem extends BaseEmptyItem
    {
        public String account;//账户或者手机号
        public String checkcode;//验证码  手机端动态生成，
    }

    /**
     * 验证手机号码需要post提交的实体类
     */

    public static  class PostPhoneItem extends  BaseEmptyItem
    {
        public String account;//账户或者手机号
        public String  checkcode;//验证码
    }

    /***
     * 验证常用就诊人需要post提交的实体类
     */
    public  static  class  PostPatientItem extends  BaseEmptyItem
    {
        public String  account;//账户或者手机号
        public String    patient;//常用就诊人姓名
    }

    /***
     * 重置密码需要post提交的实体类
     */
    public  static  class  PostResetPwdItem extends  BaseEmptyItem
    {
        public String   account;//账户或者手机号
        public String     newpassword;//新密码经过md5加密
    }
}
