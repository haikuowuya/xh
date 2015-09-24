package com.xinheng.mvp.presenter.impl;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.OkHttpUtils;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.subscribe.PostSubmitSubscribeItem;
import com.xinheng.mvp.presenter.SubmitSubscribePresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.DebugUtils;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 提交预约挂号的接口实现
 */
public class SubmitSubscribePresenterImpl implements SubmitSubscribePresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;

    private String mRequestTag;

    public SubmitSubscribePresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    public SubmitSubscribePresenterImpl(BaseActivity activity, DataView dataView, String requestTag)
    {
        mActivity = activity;
        mDataView = dataView;
        mRequestTag = requestTag;
    }

    @Override
    public void doSubmitSubscribe(PostSubmitSubscribeItem item)
    {
        String submitSubscribeUrl = APIURL.SUBMIT_SUBSCRIBE_URL;
        // item.userId = RSAUtil.clientEncrypt(mActivity.getLoginSuccessItem().id);//特殊。 userId需要加密
        String mingPostBody = GsonUtils.toJson(item);
        System.out.println("mingPostBody = " + mingPostBody);
        Callback callback = new Callback()
        {
            @Override
            public void onFailure(Request request, final IOException e)
            {
                mActivity.runOnUiThread(
                        new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                mActivity.dismissProgressDialog();
                                mDataView.onGetDataFailured(e.getMessage(), mRequestTag);
                            }
                        });
            }

            @Override
            public void onResponse(final Response response) throws IOException
            {
                final String result = (response != null) ? response.body().string() : null;
                ResultItem resultItem = null;
                if (null != result)
                {
                    //解密返回的结果
                    String decryptResult = RSAUtil.clientDecrypt(result);
                    if (DebugUtils.isShowDebug(mActivity))
                    {
                        System.out.println("结果数据 = 【 " + result + " 】");
                        System.out.println("解密后结果数据 = 【 " + decryptResult + " 】");
                    }
                    resultItem = GsonUtils.jsonToClass(decryptResult, ResultItem.class);
                }
                final ResultItem finalResultItem = resultItem;
                mActivity.runOnUiThread(
                        new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                mActivity.dismissProgressDialog();
                                mDataView.onGetDataSuccess(finalResultItem, mRequestTag);
                            }
                        });
            }
        };
        Map<String, String> postMap = new HashMap<>();
        postMap.put("userId", item.userId);
        postMap.put("scheduleId", item.scheduleId);
        postMap.put("patientId", item.patientId);
        postMap.put("status", item.status);
        postMap.put("conditionReport", item.conditionReport);
        postMap.put("symptoms", item.symptoms);
        postMap.put("bmrIds", GsonUtils.toJson(item.bmrIds));
        postMap.put("auths", GsonUtils.toJson(item.auths));
        mActivity.showProgressDialog();
        OkHttpUtils.customXHasyncExecuteWithFile(submitSubscribeUrl, mActivity.getLoginSuccessItem(), postMap, item.files, callback);
    }

}
