package com.xinheng.http;

import android.text.TextUtils;

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
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by raiyi-suzhou on 2015/5/20 0020.
 */
public class OkHttpUtils
{
    private static final OkHttpClient sOkHttpClient = new OkHttpClient();

    static
    {
        sOkHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);
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

    public static void customXHasyncExecute(String url, LoginSuccessItem loginSuccessItem, String postBody, Callback callback)
    {
        //userId要客户端加密
        String encryptUserId = RSAUtil.clientEncrypt(loginSuccessItem.id);
        System.out.println("加密后的userId = " + encryptUserId);
        String sessionId = loginSuccessItem.sessionId;
        Headers headers = new Headers.Builder().add(Constants.SESSION_ID, sessionId).add(Constants.COOKIE, Constants.SID + sessionId).add(Constants.USER_ID, encryptUserId).build();
        RequestBody reqBody = RequestBody.create(MediaType.parse("application/json"), postBody);
        Request request = new Request.Builder().url(url).headers(headers).post(reqBody).build();
        asyncExecute(request, callback);
    }

    public static void customXHAsyncExecuteWithFile(String url, LoginSuccessItem loginSuccessItem, Map<String, String> postMap, List<File> files, Callback callback)
    {
        System.out.println("XH  请求URL = " + url);
        //userId要客户端加密
        String encryptUserId = RSAUtil.clientEncrypt(loginSuccessItem.id);
        System.out.println("加密后的userId = " + encryptUserId);
        String sessionId = loginSuccessItem.sessionId;
        Headers headers = new Headers.Builder().add(Constants.SESSION_ID, sessionId).add(Constants.COOKIE, Constants.SID + sessionId).add(Constants.USER_ID, encryptUserId).build();
        MultipartBuilder multipartBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);
        //添加提交的文件列表
        if (null != files && !files.isEmpty())
        {
            for (File file : files)
            {
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
                String fileName = file.getName();
                if(!fileName.endsWith(".png"))
                {
                    fileName = fileName+".png";
                }
                multipartBuilder.addFormDataPart("files", fileName, fileBody);
            }
        }
        if (null != postMap)
        {
            for (String key : postMap.keySet())
            {
                String value = postMap.get(key);
                if (TextUtils.isEmpty(value))
                {
                    value = "";
                }
                multipartBuilder.addFormDataPart(key, value);
            }
        }
        RequestBody reqBody = multipartBuilder.build();
        Request request = new Request.Builder().url(url).headers(headers).post(reqBody).build();
        asyncExecute(request, callback);
    }






    public static void customXHAddMedicalRecordAsyncExecuteWithFile(String url, LoginSuccessItem loginSuccessItem, Map<String, String> postMap, List<File> sickfiles, List<File> reportfiles, List<File> prescfiles, Callback callback)
    {
        System.out.println("XH  请求URL = " + url);
        //userId要客户端加密
        String encryptUserId = RSAUtil.clientEncrypt(loginSuccessItem.id);
        System.out.println("加密后的userId = " + encryptUserId);
        String sessionId = loginSuccessItem.sessionId;
        Headers headers = new Headers.Builder().add(Constants.SESSION_ID, sessionId).add(Constants.COOKIE, Constants.SID + sessionId).add(Constants.USER_ID, encryptUserId).build();
        MultipartBuilder multipartBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);
        //添加提交的文件列表   诊疗图片，文件流提交
        if (null != sickfiles && !sickfiles.isEmpty())
        {
            for (File file : sickfiles)
            {
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
                String fileName = file.getName();
                if(!fileName.endsWith(".png"))
                {
                    fileName = fileName+".png";
                }
                multipartBuilder.addFormDataPart("sickfiles", fileName, fileBody);
            }
        }

        //添加提交的文件列表  检查报告图片，文件流提交
        if (null != reportfiles && !reportfiles.isEmpty())
        {
            for (File file : reportfiles)
            {
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
                String fileName = file.getName();
                if(!fileName.endsWith(".png"))
                {
                    fileName = fileName+".png";
                }
                multipartBuilder.addFormDataPart("reportfiles", fileName, fileBody);
            }
        }

        //添加提交的文件列表  处方图片，文件流提交
        if (null != prescfiles && !prescfiles.isEmpty())
        {
            for (File file : prescfiles)
            {
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
                String fileName = file.getName();
                if(!fileName.endsWith(".png"))
                {
                    fileName = fileName+".png";
                }
                multipartBuilder.addFormDataPart("prescfiles", fileName, fileBody);
            }
        }



        if (null != postMap)
        {
            for (String key : postMap.keySet())
            {
                String value = postMap.get(key);
                if (TextUtils.isEmpty(value))
                {
                    value = "";
                }
                multipartBuilder.addFormDataPart(key, value);
            }
        }
        RequestBody reqBody = multipartBuilder.build();
        Request request = new Request.Builder().url(url).headers(headers).post(reqBody).build();
        asyncExecute(request, callback);
    }








    public static void customXHAsyncExecuteWithFile(String url, LoginSuccessItem loginSuccessItem, Map<String, String> postMap, File file, Callback callback)
    {
        System.out.println("XH  请求URL = " + url);
        //userId要客户端加密
        String encryptUserId = RSAUtil.clientEncrypt(loginSuccessItem.id);
        System.out.println("加密后的userId = " + encryptUserId);
        String sessionId = loginSuccessItem.sessionId;
        Headers headers = new Headers.Builder().add(Constants.SESSION_ID, sessionId).add(Constants.COOKIE, Constants.SID + sessionId).add(Constants.USER_ID, encryptUserId).build();
        MultipartBuilder multipartBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);
        //添加提交的文件列表
        if (null != file)
        {
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
            multipartBuilder.addFormDataPart("file", file.getName(), fileBody);
        }
        if (null != postMap)
        {
            for (String key : postMap.keySet())
            {
                String value = postMap.get(key);
                if (TextUtils.isEmpty(value))
                {
                    value = "";
                }
                multipartBuilder.addFormDataPart(key, value);
            }
        }
        RequestBody reqBody = multipartBuilder.build();
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
