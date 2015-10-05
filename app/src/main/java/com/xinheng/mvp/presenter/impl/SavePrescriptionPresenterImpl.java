package com.xinheng.mvp.presenter.impl;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.OkHttpUtils;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.prescription.PostSavePrescriptionItem;
import com.xinheng.mvp.presenter.SavePrescriptionPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.DebugUtils;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户保存药方接口实现
 */
public class SavePrescriptionPresenterImpl implements SavePrescriptionPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;

    public SavePrescriptionPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    @Override
    public void doSavePrescription(PostSavePrescriptionItem item)
    {
        String userMedicalUrl = APIURL.SAVE_PRESCRIPTION_URL;
        item.userId = mActivity.getLoginSuccessItem().id;
        String mingPostBody = GsonUtils.toJson(item);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        System.out.println("postBody = " + postBody);
//       RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, userMedicalUrl, postBody, mActivity.getLoginSuccessItem(), mDataView);
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
                        mDataView.onGetDataFailured(e.getMessage(),null);
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
                        mDataView.onGetDataSuccess(finalResultItem,null);
                    }
                });
            }
        };
        Map<String, String> postMap = new HashMap<>();
        postMap.put("userId", RSAUtil.clientEncrypt(mActivity.getLoginSuccessItem().id));
        postMap.put("name", item.name);
        String drugIdStr = GsonUtils.toJson(item.drugId);
        // System.out.println("drugIdStr = " +drugIdStr);
        postMap.put("drugIds", drugIdStr);
        postMap.put("hosname", item.hosname);
        postMap.put("doctorname", item.doctorname);
        postMap.put("patientname", item.patientname);
        postMap.put("result", item.result);
        postMap.put("quantity", item.quantity);
        postMap.put("drugQuantitys", GsonUtils.toJson(item.drugQuantity));
        mActivity.showProgressDialog();
        OkHttpUtils.customXHAsyncExecuteWithFile(userMedicalUrl, mActivity.getLoginSuccessItem(), postMap, item.file, callback);
    }
}
