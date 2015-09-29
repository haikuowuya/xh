package com.xinheng.common;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xinheng.base.BaseActivity;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.DebugUtils;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

import java.io.IOException;

/**
 * 使用Ok http 框架处理与图片有关的接口时结果的回调执行实现
 */
public class OkHttpCallbackImpl implements Callback
{
    private BaseActivity mActivity;
    private DataView mDataView;

    public OkHttpCallbackImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    @Override
    public void onFailure(Request request, final IOException e)
    {
        mActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                mActivity.dismissProgressDialog();
                mDataView.onGetDataFailured(e.getMessage(), null);
            }
        });
    }

    @Override
    public void onResponse(Response response) throws IOException
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
        mActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                mActivity.dismissProgressDialog();
                mDataView.onGetDataSuccess(finalResultItem, null);
            }
        });
    }
}
