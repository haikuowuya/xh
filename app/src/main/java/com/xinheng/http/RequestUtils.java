package com.xinheng.http;

import android.os.Handler;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.xinheng.base.BaseActivity;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.DebugUtils;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/20 0020
 * 时间： 15:28
 * 说明：网络请求统一管理类
 */
public class RequestUtils {
    /**
     * 请求url中的数据，默认用GET请求
     *
     * @param activity
     * @param url:请求的URL
     * @param dataView:请求结果的回调
     */
    public static void getDataFromUrl(final BaseActivity activity, String url, final DataView dataView) {
        Response.Listener responseListener = new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                final String result = (response != null) ? response.toString() : null;
                if (DebugUtils.isShowDebug(activity)) {
                    //  System.out.println("结果数据 = 【 "+result+" 】");
                }
                //模拟网络请求
                new Handler(activity.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        activity.dismissProgressDialog();
//                        dataView.onGetDataSuccess(GsonUtils.jsonToClass(result, ResultItem.class));
                        dataView.onGetDataSuccess(null);
                    }
                }, 2000L);

            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                activity.dismissProgressDialog();
                dataView.onGetDataFailured(error.getMessage());
            }
        };
        Request request = new StringRequest(Request.Method.GET, url, responseListener, errorListener);
        VolleyUtils.addRequest(activity, request);
    }


    public static void getDataFromUrlByPost(final BaseActivity activity, String url, final String postBody, final DataView dataView) {
        getDataFromUrlByPostWithSessionId(activity, url, postBody, null, dataView);
    }


    public static void getDataFromUrlByPostWithSessionId(final BaseActivity activity, String url, final String postBody, final String sessionId, final DataView dataView) {
        Response.Listener responseListener = new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                activity.dismissProgressDialog();
                ResultItem resultItem = null;
                final String result = (response != null) ? response.toString() : null;
                if (null != result) {

                    //解密返回的结果
                    String decryptResult = RSAUtil.clientDecrypt(result);
                    if (DebugUtils.isShowDebug(activity)) {
                        System.out.println("结果数据 = 【 " + result + " 】");
                        System.out.println("解密后结果数据 = 【 " + decryptResult + " 】");
                    }
                    resultItem = GsonUtils.jsonToClass(decryptResult, ResultItem.class);
                }
                dataView.onGetDataSuccess(resultItem);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                activity.dismissProgressDialog();
                dataView.onGetDataFailured(error.getMessage());
            }
        };
        Request request = new StringRequest(Request.Method.POST, url, responseListener, errorListener) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                byte[] bytes = null;
                try {
                    bytes = postBody.getBytes(HTTP.UTF_8);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return bytes;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (!TextUtils.isEmpty(sessionId)) {
                    HashMap<String, String> headerMap = new HashMap<>();
                    headerMap.put("sessionId", sessionId);
                    return headerMap;
                }
                return super.getHeaders();
            }
        };
        VolleyUtils.addRequest(activity, request);
        activity.showProgressDialog();
    }


}
