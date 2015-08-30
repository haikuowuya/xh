package com.xinheng.mvp.presenter.impl;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.presenter.OnLinePresenter;
import com.xinheng.mvp.view.DataView;

/**
 * Created by Administrator on 2015/8/29 0029.
 */
public class OnLinePresenterImpl implements OnLinePresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;

    public OnLinePresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    @Override
    public void doGetOnLine()
    {
          String onLineUrl = APIURL.ONLINE_URL;
         String postBody = null;
        RequestUtils.getDataFromUrlByPost(mActivity,onLineUrl,postBody,mDataView);
    }
}
