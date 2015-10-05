package com.xinheng.mvp.presenter.impl;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.OkHttpUtils;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.user.PostAddRecipeItem;
import com.xinheng.mvp.presenter.SubmitAddRecipePresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.DebugUtils;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 提交添加处方的接口实现
 */
public class SubmitAddRecipePresenterImpl implements SubmitAddRecipePresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;

    private String mRequestTag;

    public SubmitAddRecipePresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    public SubmitAddRecipePresenterImpl(BaseActivity activity, DataView dataView, String requestTag)
    {
        mActivity = activity;
        mDataView = dataView;
        mRequestTag = requestTag;
    }

    @Override
    public void doAddRecipe(PostAddRecipeItem item)
    {
        String submitSubscribeUrl = APIURL.SUBMIT_ADD_RECIPE_URL;
        String mingPostBody = GsonUtils.toJson(item);
        System.out.println("mingPostBody = " + mingPostBody);
        Callback callback = new Callback()
        {
            @Override
            public void onFailure(Request request, final IOException e)
            {
                mActivity.runOnUiThread(new Runnable()
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
                mActivity.runOnUiThread(new Runnable()
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
        postMap.put("institution", item.institution);
        postMap.put("prescTime", item.prescTime);
        postMap.put("name", item.name);
        mActivity.showProgressDialog();
        OkHttpUtils.customXHAsyncExecuteWithFile(submitSubscribeUrl, mActivity.getLoginSuccessItem(), postMap, item.files, callback);
    }


}
