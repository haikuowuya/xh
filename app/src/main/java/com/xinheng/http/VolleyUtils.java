package com.xinheng.http;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.squareup.okhttp.OkHttpClient;
import com.xinheng.base.BaseActivity;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/5 0005
 * 时间： 10:17
 * 说明：
 */
public class VolleyUtils
{
    private static RequestQueue sRequestQueue = null;

    private  static RequestQueue getRequestQueueInstance(BaseActivity activity)
    {
        if (sRequestQueue == null)
        {
            sRequestQueue = Volley.newRequestQueue(activity, new OkHttpStack(new OkHttpClient()));
        }
        return sRequestQueue;
    }

    /**
     * Adds a request to the Volley request queue
     *
     * @param request is the request to add to the Volley queue
     */
    public static void addRequest(BaseActivity baseActivity, Request<?> request)
    {
        request.setRetryPolicy(new DefaultRetryPolicy(15 * 1000, 1, 1.0f));
            addRequest(baseActivity, request, baseActivity.getActivityTitle());



    }

    /**
     * Adds a request to the Volley request queue with a given tag
     *
     * @param request is the request to be added
     * @param tag     tag identifying the request
     */
    public static void addRequest(BaseActivity baseActivity, Request<?> request, CharSequence tag)
    {
        request.setTag(tag);
        getRequestQueueInstance(baseActivity).add(request);
    }

    /**
     * Cancels all the request in the Volley queue for a given tag
     *
     * @param tag associated with the Volley requests to be cancelled
     */
    public static void cancelAllRequests(BaseActivity baseActivity, String tag)
    {
        if (getRequestQueueInstance(baseActivity) != null)
        {
            getRequestQueueInstance(baseActivity).cancelAll(tag);
        }
    }

}
