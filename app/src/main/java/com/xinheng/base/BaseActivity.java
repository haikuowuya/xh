package com.xinheng.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.xinheng.R;
import com.xinheng.XHApplication;
import com.xinheng.drawable.DrawerArrowDrawable;
import com.xinheng.http.VolleyUtils;
import com.xinheng.util.IntentUtils;
import com.xinheng.util.ToastUtils;
import com.xinheng.util.ViewUtils;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 *
 */
public abstract class BaseActivity extends AppCompatActivity implements IActivityTitle
{
    /**
     * Activity的一个实例{@link  BaseActivity#onCreate(Bundle) }
     */
    protected BaseActivity mActivity;
    /**
     * {@link SharedPreferences}的一个实例
     */
    protected SharedPreferences mPreferences;
    /**
     * 继承自baseActivity的外层容器{@link  BaseActivity#setContentView(int)}
     * }
     */
    private FrameLayout mFrameContainer;
    private PtrClassicFrameLayout mPtrClassicFrameLayout;

    private TextView mTvCenterTitle;
    /**
     * 左上角的返回按钮
     */
    private ImageView mIvBack;

    public SharedPreferences getPreferences()
    {
        return mPreferences;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        int statusCoclor = getResources().getColor(R.color.color_title_background_color);
        ViewUtils.alphaStatusBarAndNavBar(mActivity, statusCoclor, 0xFF000000);
    }

    public void showToast(String text)
    {
        Toast.makeText(mActivity, text, Toast.LENGTH_SHORT).show();
    }

    public void showCroutonToast(String text)
    {
        ToastUtils.showCrouton(mActivity, text, getContentViewGroup());
    }

    @Override
    public void setContentView(int layoutResID)
    {
        super.setContentView(R.layout.activity_base);   //TODO XXX
        mFrameContainer = (FrameLayout) findViewById(R.id.frame_container);
        mPtrClassicFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.ptr_container);
        mTvCenterTitle = (TextView) findViewById(R.id.tv_center_title);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvCenterTitle.setText(getActivityTitle());
        View contentView = LayoutInflater.from(mActivity).inflate(layoutResID, null);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        mFrameContainer.addView(contentView, layoutParams);
        mIvBack.setImageDrawable(new DrawerArrowDrawable.BackDrawerArrowDrawable(mActivity));
        if (IntentUtils.isLauncherIntent(getIntent()))
        {
            mIvBack.setVisibility(View.GONE);
        }
        mIvBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
        mPtrClassicFrameLayout.setPtrHandler(new PtrHandlerImpl());
        XHApplication application = (XHApplication) getApplication();
        application.recordActivity(mActivity);
    }

    public ViewGroup getContentViewGroup()
    {
        return mFrameContainer;
    }

    public void requestGetDataFromUrl(String url)
    {
        ListenerImpl listener = new ListenerImpl();
        Request request = new StringRequest(Request.Method.GET, url, listener, listener);
        VolleyUtils.addRequest(mActivity, request);
    }

    public void onGetDataSuccess(String string)
    {
        System.out.println("result string = " + string);
    }

    private class ListenerImpl implements Response.Listener, Response.ErrorListener
    {
        public void onErrorResponse(VolleyError error)
        {

        }

        @Override
        public void onResponse(Object response)
        {
            String result = (response != null) ? response.toString() : null;
            onGetDataSuccess(result);
        }
    }

    public boolean canDoRefresh()
    {
        return true;
    }

    private class PtrHandlerImpl   implements PtrHandler
    {
        @Override
        public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view1)
        {
            return canDoRefresh();
        }

        @Override
        public void onRefreshBegin(PtrFrameLayout ptrFrameLayout)
        {
            ptrFrameLayout.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        mPtrClassicFrameLayout.refreshComplete();
                    }
                }, 2000L);
        }
    }


}
