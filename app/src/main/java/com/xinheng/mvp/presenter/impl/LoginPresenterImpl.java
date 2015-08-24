package com.xinheng.mvp.presenter.impl;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.OkHttpUtils;
import com.xinheng.http.VolleyUtils;
import com.xinheng.mvp.model.LoginItem;
import com.xinheng.mvp.presenter.LoginPresenter;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.MD5;
import com.xinheng.util.RSAUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/24 0024
 * 时间： 16:34
 * 说明：
 */
public class LoginPresenterImpl implements LoginPresenter
{
    private BaseActivity mActivity;

    public LoginPresenterImpl(BaseActivity baseActivity)
    {
        mActivity = baseActivity;
    }

    @Override
    public void doLogin(String username, String password)
    {
        //   testVolleyLogin();
        testOkHttpLogin();
    }

    public void testOkHttpLogin()
    {
        String pwd = new MD5().getMD5_32("110916");
        LoginItem loginItem = new LoginItem("15850217017", pwd);
        final String jsonLoginItem = GsonUtils.toJson(loginItem);
        System.out.println("jsonLoginItem = " + jsonLoginItem);
        final String encryptString = RSAUtil.clientEncrypt(jsonLoginItem);
        System.out.println("客户端加密的字符串 = " + encryptString);
        String loginUrl = "http://139.196.24.205:8080/interface/patient/user/login";

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), encryptString);
        com.squareup.okhttp.Request okRequest = new com.squareup.okhttp.Request.Builder().url(loginUrl).post(body).build();
        OkHttpUtils.asyncExecute(okRequest, new Callback()
        {
            @Override
            public void onFailure(com.squareup.okhttp.Request request, IOException e)
            {
                System.out.println("okhttp error  = " + e.getMessage());
            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) throws IOException
            {
                String str = response.body().string();
                if (!TextUtils.isEmpty(str))
                {
                    String result = RSAUtil.clientDecrypt(str);
                    System.out.println("result = " + result);
                }
            }
        });
    }

    public void testVolleyLogin()
    {
        String pwd = new MD5().getMD5_32("110916");
        LoginItem loginItem = new LoginItem("15850217017", pwd);
        String loginUrl = "http://139.196.24.205:8080/interface/patient/user/login";
        final String jsonLoginItem = GsonUtils.toJson(loginItem);
        System.out.println("jsonLoginItem = " + jsonLoginItem);
        final String encryptString = RSAUtil.clientEncrypt(jsonLoginItem);
        final String decryptString = RSAUtil.serverDecrypt(encryptString);
        System.out.println("客户端加密的字符串 = " + encryptString);
        System.out.println("服务端解密的字符串 = " + decryptString);
        ListenerImpl listener = new ListenerImpl();
        Request request = new StringRequest(Request.Method.POST, loginUrl, listener, listener)
        {
            @Override
            public byte[] getBody() throws AuthFailureError
            {
                byte[] bytes = null;
                try
                {
                    bytes = encryptString.getBytes("UTF-8");
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
                return bytes;
            }
        };
        VolleyUtils.addRequest(mActivity, request);
    }

    private class ListenerImpl implements Response.Listener, Response.ErrorListener
    {
        @Override
        public void onErrorResponse(VolleyError error)
        {
            System.out.println("error = " + error.getMessage());
        }

        @Override
        public void onResponse(Object response)
        {
            System.out.println("response = " + response.toString());
        }
    }
}
