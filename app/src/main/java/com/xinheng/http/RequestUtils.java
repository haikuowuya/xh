package com.xinheng.http;

import android.os.Handler;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.xinheng.base.BaseActivity;
import com.xinheng.mvp.model.LoginSuccessItem;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.presenter.AutoLoginPresenter;
import com.xinheng.mvp.presenter.impl.AutoLoginPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.Constants;
import com.xinheng.util.DebugUtils;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/20 0020
 * 时间： 15:28
 * 说明：网络请求统一管理类
 */
public class RequestUtils
{
    public static final String ERROR_SERVER = "服务器开小差了";

    /**
     * 请求url中的数据，默认用{@link com.android.volley.Request.Method#GET} get请求
     *
     * @param activity
     * @param url:请求的URL
     * @param dataView:请求结果的回调
     */
    public static void getDataFromUrl(final BaseActivity activity, String url, final DataView dataView, final String requestTag)
    {
        Response.Listener responseListener = new Response.Listener()
        {
            @Override
            public void onResponse(Object response)
            {
                final String result = (response != null) ? response.toString() : null;
                if (DebugUtils.isShowDebug(activity))
                {
                    //  System.out.println("结果数据 = 【 "+result+" 】");
                }
                //模拟网络请求
                new Handler(activity.getMainLooper()).postDelayed(
                        new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                activity.dismissProgressDialog();
//                        dataView.onGetDataSuccess(GsonUtils.jsonToClass(result, ResultItem.class));
                                dataView.onGetDataSuccess(null, requestTag);
                            }
                        }, 2000L);

            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                activity.dismissProgressDialog();
                dataView.onGetDataFailured(error.getMessage(), requestTag);
            }
        };
        Request request = new StringRequest(Request.Method.GET, url, responseListener, errorListener);
        VolleyUtils.addRequest(activity, request);
    }

    /**
     * 使用Post请求
     *
     * @param activity :当前请求所在的Activity
     * @param url      ：post请求的url
     * @param postBody :post请求的加密字符串
     * @param dataView ：处理加载进度
     */
    public static void getDataFromUrlByPost(final BaseActivity activity, String url, final String postBody, final DataView dataView, String requestTag)
    {
        getDataFromUrlByPostWithLoginInfo(activity, url, postBody, null, dataView, requestTag);
    }

    public static void getDataFromUrlByPost(final BaseActivity activity, String url, final String postBody, final DataView dataView)
    {
        getDataFromUrlByPostWithLoginInfo(activity, url, postBody, null, dataView);
    }

    /***
     * 获取用户登录成功后的数据， 使用Post请求,没有传人requestTag,适用于一个页面只进行单一请求
     *
     * @param activity    : 当前请求所在的Activity
     * @param url         : post请求的url
     * @param postBody    : post请求的加密字符串
     * @param successItem :用户登录成功后的信息
     * @param dataView    :处理加载进度
     */
    public static void getDataFromUrlByPostWithLoginInfo(final BaseActivity activity, String url, final String postBody, final LoginSuccessItem successItem, final DataView dataView)
    {
        getDataFromUrlByPostWithLoginInfo(activity, url, postBody, successItem, dataView, null);
    }

    /**
     * 获取用户登录成功后的数据， 使用Post请求
     *
     * @param activity         :当前请求所在的Activity
     * @param url              ：post请求的url
     * @param postBody         :post请求的加密字符串
     * @param dataView         ：处理加载进度
     * @param successItem:     用户登录成功后的信息
     * @param requestTag:请求的标志
     */
    /*
    public static void getDataFromUrlByPostWithLoginInfo(final BaseActivity activity, String url, final String postBody, final LoginSuccessItem successItem, final DataView dataView, final String requestTag)
    {
        System.out.println("请求的URL = " + url);
        Response.Listener responseListener = new Response.Listener()
        {
            public void onResponse(Object response)
            {
                activity.dismissProgressDialog();
                ResultItem resultItem = null;
                final String result = (response != null) ? response.toString() : null;
                if (null != result)
                {
                    //解密返回的结果
                    String decryptResult = RSAUtil.clientDecrypt(result);
                    if (DebugUtils.isShowDebug(activity))
                    {
                        System.out.println("结果数据 = 【 " + result + " 】");
                        System.out.println("解密后结果数据 = 【 " + decryptResult + " 】");
                    }
                    resultItem = GsonUtils.jsonToClass(decryptResult, ResultItem.class);
                }
                dataView.onGetDataSuccess(resultItem, requestTag);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                activity.dismissProgressDialog();
                String errMsg = "错误信息没有";
                if (error != null && !TextUtils.isEmpty(error.getMessage()))
                {
                    errMsg = error.getMessage();
                }
                System.out.println("errMsg = " + errMsg + " error = " + error);
                dataView.onGetDataFailured(errMsg, requestTag);
            }
        };
        Request request = new StringRequest(Request.Method.POST, url, responseListener, errorListener)
        {
            public byte[] getBody() throws AuthFailureError
            {
                byte[] bytes = null;
                if (!TextUtils.isEmpty(postBody))
                {
                    try
                    {
                        bytes = postBody.getBytes(HTTP.UTF_8);
                    } catch (UnsupportedEncodingException e)
                    {
                        e.printStackTrace();
                    }
                }
                return bytes;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                if (successItem != null)
                {
                    HashMap<String, String> headerMap = new HashMap<>();
                    headerMap.put(Constants.SESSION_ID, successItem.sessionId);
                    headerMap.put(Constants.USER_AGENT_KEY, Constants.USER_AGENT_VALUE);
                    headerMap.put(Constants.COOKIE, Constants.SID + successItem.sessionId);
                    //userId要客户端加密
                    String encryptUserId = RSAUtil.clientEncrypt(successItem.id);
//                    System.out.println("加密后的userId = " + encryptUserId);
                    headerMap.put(Constants.USER_ID, encryptUserId);
                    return headerMap;
                }
                return super.getHeaders();
            }
        };
        VolleyUtils.addRequest(activity, request);
        activity.showProgressDialog();
    }
    */
    public static void getDataFromUrlByPostWithLoginInfo(final BaseActivity activity, String url, final String postBody, final LoginSuccessItem successItem, final DataView dataView, final String requestTag)
    {
        getDataFromUrlByPostWithLoginInfo(activity, url, postBody, successItem, dataView, requestTag, true);
    }

    /**
     * 获取用户登录成功后的数据， 使用Post请求
     *
     * @param activity                     :当前请求所在的Activity
     * @param url                          ：post请求的url
     * @param postBody                     :post请求的加密字符串
     * @param dataView                     ：处理加载进度
     * @param successItem                  :用户登录成功后的信息
     * @param requestTag:请求的标志
     * @param showProgressDialog:是否显示进度对话框
     */
    public static void getDataFromUrlByPostWithLoginInfo(final BaseActivity activity, String url, final String postBody, final LoginSuccessItem successItem, final DataView dataView, final String requestTag, final boolean showProgressDialog)
    {
        System.out.println("请求的URL = " + url);
        Request request = new Builder().baseActivity(activity).dataView(dataView).url(url).requestTag(requestTag).loginSuccessItem(successItem).postBody(postBody).showProgressDialog(showProgressDialog).build();
        if (null != request)
        {
            VolleyUtils.addRequest(activity, request);
        } else
        {
            System.out.println("activity 为 null ");
        }
    }

    public static class Builder
    {
        /**
         * 请求方式 ,默认post请求
         */
        private int mMethod = Request.Method.POST;
        /***
         * 请求的url
         */
        private String mUrl;
        /***
         * 请求发生时所在的Activity
         */
        private BaseActivity mActivity;
        /***
         * Post请求时的请求体
         */
        private String mPostBody;
        /***
         * 带有登录成功信息的请求
         */
        private LoginSuccessItem mLoginSuccessItem;
        /***
         * 处理结果
         */
        private DataView mDataView;
        /***
         * 请求的TAG
         */
        private String mRequestTag;
        /**
         * 是否显示进度对话框 ,默认显示
         */
        private boolean mShowProgressDialog = true;

        public Builder url(String url)
        {
            this.mUrl = url;
            return this;
        }

        public Builder method(int method)
        {
            this.mMethod = method;
            return this;
        }

        public Builder baseActivity(BaseActivity baseActivity)
        {
            this.mActivity = baseActivity;
            return this;
        }

        public Builder postBody(String postBody)
        {
            this.mPostBody = postBody;
            return this;
        }

        public Builder loginSuccessItem(LoginSuccessItem loginSuccessItem)
        {
            this.mLoginSuccessItem = loginSuccessItem;
            return this;
        }

        public Builder dataView(DataView dataView)
        {
            this.mDataView = dataView;
            return this;
        }

        public Builder showProgressDialog(boolean showProgressDialog)
        {
            this.mShowProgressDialog = showProgressDialog;
            return this;
        }

        public Builder requestTag(String requestTag)
        {
            this.mRequestTag = requestTag;
            return this;
        }

        /**
         * 将post请求的字符串转化为byte数组类型
         *
         * @return
         */
        private byte[] getBuilderPostBody() throws AuthFailureError
        {
            byte[] bytes = null;
            if (!TextUtils.isEmpty(mPostBody))
            {
                try
                {
                    bytes = mPostBody.getBytes(HTTP.UTF_8);
                } catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
            }
            return bytes;
        }

        /***
         * 获取有登录信息时的HTTP头信息
         *
         * @return
         */
        private Map<String, String> getBuidlerHeaders()
        {
            if (null != mLoginSuccessItem)
            {
                HashMap<String, String> headerMap = new HashMap<>();
                headerMap.put(Constants.SESSION_ID, mLoginSuccessItem.sessionId);
                headerMap.put(Constants.USER_AGENT_KEY, Constants.USER_AGENT_VALUE);
                headerMap.put(Constants.COOKIE, Constants.SID + mLoginSuccessItem.sessionId);
                //userId要客户端加密
                String encryptUserId = RSAUtil.clientEncrypt(mLoginSuccessItem.id);
//                    System.out.println("加密后的userId = " + encryptUserId);
                headerMap.put(Constants.USER_ID, encryptUserId);
                return headerMap;
            }
            return Collections.emptyMap();
        }

        public Request build()
        {
            Request request = null;
            if (mActivity != null)
            {
                /***
                 * 请求结束的回调监听
                 */
                Response.Listener<String> responseListener = new Response.Listener<String>()
                {
                    public void onResponse(String response)
                    {
                        if (null != mActivity && null != mDataView)
                        {
                            if (mShowProgressDialog)
                            {
                                mActivity.dismissProgressDialog();
                            }
                            ResultItem resultItem = null;
                            final String result = (response != null) ? response.toString() : null;
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
                                if (null != resultItem && resultItem.sessionIsExpired())
                                {
                                    AutoLoginPresenter autoLoginPresenter = new AutoLoginPresenterImpl(mActivity);
                                    autoLoginPresenter.doAutoLogin();
                                    return;
                                }
                            }
                            mDataView.onGetDataSuccess(resultItem, mRequestTag);
                        }
                    }
                };
                /***
                 * 请求出错时的回调监听
                 */
                Response.ErrorListener errorListener = new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        if (mShowProgressDialog)
                        {
                            mActivity.dismissProgressDialog();
                        }
                        String errMsg = ERROR_SERVER;
                        if (error != null && !TextUtils.isEmpty(error.getMessage()))
                        {
                            errMsg = error.getMessage();
                        }
                        //   System.out.println("errMsg = " + errMsg + " error = " + error);
                        if (null != mDataView)
                        {
                            mDataView.onGetDataFailured(errMsg, mRequestTag);
                        }
                    }
                };
                request = new StringRequest(mMethod, mUrl, responseListener, errorListener)
                {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError
                    {
                        return getBuidlerHeaders();
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError
                    {
                        return getBuilderPostBody();
                    }
                };
                if (mShowProgressDialog)
                {
                    mActivity.showProgressDialog();
                }
            }
            return request;
        }
    }
}
