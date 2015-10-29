package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.PostItem;
import com.xinheng.mvp.presenter.ModifyPwdPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 作者： libo
 * 日期： 2015/8/24 0024
 * 时间： 16:34
 * 说明：修改密码接口的实现类
 */
public class ModifyPwdPresenterImpl implements ModifyPwdPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;
    private String mRequestTag;

    public ModifyPwdPresenterImpl(BaseActivity activity, DataView dataView, String requestTag)
    {

        mActivity = activity;
        mDataView = dataView;
        mRequestTag = requestTag;
    }

    @Override
    public  void  doModifyPwd(String phone,String pwd)
    {
        String departDoctorUrl = APIURL.MODIFY_PWD_URL;
        PostPModifyPwdItem item = new PostPModifyPwdItem();
        item.mobile = phone;
        item.password = pwd;
        String mingPostBody = GsonUtils.toJson(item);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, departDoctorUrl, postBody, mActivity.getLoginSuccessItem(), mDataView, mRequestTag);
    }

    public static  class PostPModifyPwdItem extends PostItem
    {
        public String mobile;//手机号码
        public String password;//新密码

    }
}
