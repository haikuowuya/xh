package com.xinheng.http;

import android.os.Handler;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.xinheng.base.BaseActivity;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.DebugUtils;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/20 0020
 * 时间： 15:28
 * 说明：
 */
public class RequestUtils
{
    public  static void  getDataFromUrl(final BaseActivity activity, String url, final DataView dataView)
    {
        Response.Listener responseListener = new Response.Listener()
        {
            @Override
            public void onResponse(Object response)
            {
                final String result = (response != null) ? response.toString() : null;
                if(DebugUtils.isShowDebug(activity))
                {
                  //  System.out.println("结果数据 = 【 "+result+" 】");
                }
                //模拟网络请求
                new Handler(activity.getMainLooper()).postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        activity.dismissProgressDialog();
//                        dataView.onGetDataSuccess(GsonUtils.jsonToClass(result, ResultItem.class));
                        dataView.onGetDataSuccess( null);
                    }
                }, 2000L) ;

            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                activity.dismissProgressDialog();
                dataView.onGetDataFailured(error.getMessage());
            }
        } ;
        Request request = new StringRequest(Request.Method.GET, url, responseListener, errorListener);
        VolleyUtils.addRequest(activity, request);
    }

}
