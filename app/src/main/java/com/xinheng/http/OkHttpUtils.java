package com.xinheng.http;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.xinheng.mvp.model.LoginSuccessItem;
import com.xinheng.util.Constants;
import com.xinheng.util.RSAUtil;

import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by raiyi-suzhou on 2015/5/20 0020.
 */
public class OkHttpUtils
{
    private static final OkHttpClient sOkHttpClient = new OkHttpClient();

    static
    {
        sOkHttpClient.setConnectTimeout(20, TimeUnit.SECONDS);
        sOkHttpClient.setReadTimeout(20, TimeUnit.SECONDS);
    }

    /**
     * 开启异步线程访问网络
     *
     * @param request：请求
     * @param callback:回调
     */
    public static void asyncExecute(Request request, Callback callback)
    {
        sOkHttpClient.newCall(request).enqueue(callback);
    }

    public static void customXHasyncExecute(String url , LoginSuccessItem loginSuccessItem , String postBody, Callback callback)
    {
        //userId要客户端加密
        String encryptUserId = RSAUtil.clientEncrypt(loginSuccessItem.id);
        System.out.println("加密后的userId = " + encryptUserId);
        String sessionId = loginSuccessItem.sessionId;
        Headers headers = new Headers.Builder().add(Constants.SESSION_ID, sessionId)
                .add(Constants.COOKIE,  Constants.SID+sessionId)
                .add(Constants.USER_ID, encryptUserId)
                .build();
        RequestBody reqBody = RequestBody.create(MediaType.parse("application/json"), postBody);
        Request request = new Request.Builder().url(url).headers(headers).post(reqBody).build();
        asyncExecute(request, callback);
    }

    public static void customXHasyncExecuteWithFile(String url , LoginSuccessItem loginSuccessItem , String postBody,File file,  Callback callback)
    {
        //userId要客户端加密
        String encryptUserId = RSAUtil.clientEncrypt(loginSuccessItem.id);
        System.out.println("加密后的userId = " + encryptUserId);
        String sessionId = loginSuccessItem.sessionId;
        Headers headers = new Headers.Builder().add(Constants.SESSION_ID, sessionId)
                .add(Constants.COOKIE,  Constants.SID+sessionId)
                .add(Constants.USER_ID, encryptUserId)
                .build();
        RequestBody strBody = RequestBody.create(MediaType.parse("application/json"), postBody);;
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file)   ;
        RequestBody reqBody = new MultipartBuilder().type(MultipartBuilder.FORM).addFormDataPart("file","file",fileBody ).addPart(strBody).build();
        Request request = new Request.Builder().url(url).headers(headers).post(reqBody).build();
        asyncExecute(request, callback);
    }

    public static void cancleRequest(Request request)
    {
        sOkHttpClient.cancel(request);
    }

    public static void asyncExecute(Request request)
    {
        asyncExecute(request, new Callback()
        {
            @Override
            public void onFailure(Request request, IOException e)
            {
                //空实现
            }

            @Override
            public void onResponse(Response response) throws IOException
            {

                //空实现
            }
        });
    }

    /**
     * 创建线程请求，返回的字符串结果在Callback#onResponse方法中的message中
     *
     * @param url
     * @param callback
     */
    public static void asynExecuteWithThread(final String url, final Callback callback)
    {
        new Thread()
        {
            public void run()
            {
                try
                {
                    StringBuffer stringBuffer = new StringBuffer();
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
                    int responsedCode = httpURLConnection.getResponseCode();
                    if (responsedCode == HttpStatus.SC_OK)
                    {
                        InputStream intputstream = httpURLConnection.getInputStream();
                        int len = -1;
                        byte[] buffer = new byte[1024];
                        while (((len = intputstream.read(buffer)) != -1))
                        {
                            stringBuffer.append(new String(buffer, 0, len, HTTP.UTF_8));
                        }
                    }
                    String json = stringBuffer.toString();
                    System.out.println("json = " + json);
                    callback.onResponse(new Response.Builder().request(new Request.Builder().url(url).build()).code(HttpStatus.SC_OK).protocol(Protocol.HTTP_1_1).message(json).build());
                }
                catch (IOException e)
                {
                    callback.onFailure(new Request.Builder().build(), new IOException("请求发生错误"));
                }
            }
        }.start();

    }
}
