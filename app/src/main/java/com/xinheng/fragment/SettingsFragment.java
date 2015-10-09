package com.xinheng.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xinheng.LoginActivity;
import com.xinheng.R;
import com.xinheng.base.BaseFragment;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.Constants;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  系统设置Fragment页面
 */
public class SettingsFragment extends BaseFragment implements DataView
{
    private Bitmap mBitmap;

    public static SettingsFragment newInstance()
    {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    /**
     * 清除缓存
     */
    private LinearLayout mLinearCacheContainer;
    /***
     * 退出账户
     */
    private LinearLayout mLinearAccountLogoutContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_settings, null);      //TODO
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        mLinearAccountLogoutContainer = (LinearLayout) view.findViewById(R.id.linear_account_logout_container);
        mLinearCacheContainer = (LinearLayout) view.findViewById(R.id.linear_cache_container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        setListener();
    }

    private void setListener()
    {
        OnClickListenerImpl onClickListener = new OnClickListenerImpl();
        mLinearAccountLogoutContainer.setOnClickListener(onClickListener);
        mLinearCacheContainer.setOnClickListener(onClickListener);
    }

    @Override
    public String getFragmentTitle()
    {
        return getString(R.string.tv_fragment_user_account);
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem, String requestTag)
    {
        if (null != resultItem)
        {

        }
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag)
    {

    }

    private class OnClickListenerImpl implements View.OnClickListener
    {
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.linear_cache_container://
                    cache();
                    break;
                case R.id.linear_account_logout_container://
                    logout();
                    break;
            }
        }
    }

    /***
     * 退出账户
     */
    private void logout()
    {
        mActivity.getPreferences().edit().remove(Constants.PREF_RSA_USERNAME_PWD).commit();
        LoginActivity.actionLogin(mActivity);
    }

    /**
     * 清除缓存
     */
    private void cache()
    {
        mActivity.showProgressDialog("正在清除缓存文件");
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                mActivity.dismissProgressDialog();
                mActivity.showToast("清除成功");
            }
        }, 2000L);
    }

}
