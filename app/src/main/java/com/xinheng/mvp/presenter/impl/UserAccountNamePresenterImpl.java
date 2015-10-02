package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.user.PostModifyUserAccountNameItem;
import com.xinheng.mvp.presenter.UserAccountNamePresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 作者： libo
 * 日期： 2015/8/24 0024
 * 时间： 16:34
 * 说明：修改帐号昵称接口实现类
 */
public class UserAccountNamePresenterImpl implements UserAccountNamePresenter
{
    private BaseActivity mActivity;

    private DataView mDataView;

    public UserAccountNamePresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    @Override
    public void doModifyUserAccountName(String newAccuntName)
    {
        String modifyUserNicknameUrl = APIURL.MODIFY_USER_ACCOUNT_NAME_URL;
        PostModifyUserAccountNameItem item = new PostModifyUserAccountNameItem();
        item.userId = mActivity.getLoginSuccessItem().id;
        item.account = newAccuntName;
        String mingPostBody = GsonUtils.toJson(item);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, modifyUserNicknameUrl, postBody, mActivity.getLoginSuccessItem(), mDataView);

    }


}
