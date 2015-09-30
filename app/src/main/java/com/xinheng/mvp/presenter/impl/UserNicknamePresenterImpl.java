package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.user.PostModifyUsernickItem;
import com.xinheng.mvp.presenter.UserNicknamePresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

/**
 * 作者： libo
 * 日期： 2015/8/24 0024
 * 时间： 16:34
 * 说明：修改昵称接口实现类
 */
public class UserNicknamePresenterImpl implements UserNicknamePresenter
{
    private BaseActivity mActivity;

    private DataView mDataView;

    public UserNicknamePresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    @Override
    public void doModifyUserNickname(String newNickname)
    {
        String modifyUserNicknameUrl = APIURL.MODIFY_USER_NICKNAME_URL;
        PostModifyUsernickItem item = new PostModifyUsernickItem();
        item.userId = mActivity.getLoginSuccessItem().id;
        item.nickname = newNickname;
        String mingPostBody = GsonUtils.toJson(item);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, modifyUserNicknameUrl, postBody, mActivity.getLoginSuccessItem(), mDataView);

    }
}
