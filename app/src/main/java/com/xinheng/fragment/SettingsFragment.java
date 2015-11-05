package com.xinheng.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.xinheng.LoginActivity;
import com.xinheng.R;
import com.xinheng.base.BaseFragment;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.AndroidUtils;
import com.xinheng.util.Constants;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  系统设置Fragment页面
 */
public class SettingsFragment extends BaseFragment implements DataView
{
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

    /***
     * 自动登录container
     */
    private LinearLayout mLinearAutoLoginContainer;
    /**
     * 在线售药 container
     */
    private LinearLayout mLinearOnLineContainer;
    /**
     * 自己手机帐号登录container
     */
    private LinearLayout mLinearMeContainer;
    private LinearLayout mLinearSlidingMenuContainer;
    /***
     * 自动登录复选框
     */
    private CheckBox mCbAutoLogin;
    /***
     * 自己手机帐号登录复选框
     */
    private CheckBox mCbMe;
    /**
     * 在线售药界面
     */
    private CheckBox mCbOnLine;
    /**
     * 侧滑开关
     */
    private CheckBox mCbSlidingMenu;

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
        mLinearAutoLoginContainer = (LinearLayout) view.findViewById(R.id.linear_auto_login_container);
        mCbOnLine = (CheckBox) view.findViewById(R.id.cb_online);
        mCbAutoLogin = (CheckBox) view.findViewById(R.id.cb_auto_login);
        mCbMe = (CheckBox) view.findViewById(R.id.cb_me);
        mCbSlidingMenu = (CheckBox) view.findViewById(R.id.cb_slidingmenu);
        mLinearMeContainer = (LinearLayout) view.findViewById(R.id.linear_me_container);
        mLinearSlidingMenuContainer = (LinearLayout) view.findViewById(R.id.linear_slidingmenu_container);
        mLinearAccountLogoutContainer = (LinearLayout) view.findViewById(R.id.linear_account_logout_container);
        mLinearOnLineContainer = (LinearLayout) view.findViewById(R.id.linear_online_container);
        mLinearCacheContainer = (LinearLayout) view.findViewById(R.id.linear_cache_container);
        mCbAutoLogin.setChecked(mActivity.getPreferences().getBoolean(Constants.PREF_IS_AUTO_LOGIN, true));
        mCbOnLine.setChecked(mActivity.getPreferences().getBoolean(Constants.PREF_IS_ONLINE, true));
        mCbMe.setChecked(mActivity.getPreferences().getBoolean(Constants.PREF_IS_ME, true));
        mCbSlidingMenu.setChecked(mActivity.getPreferences().getBoolean(Constants.PREF_IS_SLIDING_MENU, true));
        if (Constants.IMEI.equals(AndroidUtils.getIMEI(mActivity)))
        {
            mLinearMeContainer.setVisibility(View.VISIBLE);
            mLinearSlidingMenuContainer.setVisibility(View.VISIBLE);
        }
        else
        {
            mLinearSlidingMenuContainer.setVisibility(View.GONE);
            mLinearMeContainer.setVisibility(View.GONE);
        }
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
        mLinearOnLineContainer.setOnClickListener(onClickListener);
        mLinearMeContainer.setOnClickListener(onClickListener);
        mLinearCacheContainer.setOnClickListener(onClickListener);
        mLinearAutoLoginContainer.setOnClickListener(onClickListener);
        mCbAutoLogin.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                    {
                        mActivity.getPreferences().edit().putBoolean(Constants.PREF_IS_AUTO_LOGIN, isChecked).commit();

                    }
                });
        mCbOnLine.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                    {
                        mActivity.getPreferences().edit().putBoolean(Constants.PREF_IS_ONLINE, isChecked).commit();
                    }
                });
        mCbMe.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                    {
                        mActivity.getPreferences().edit().putBoolean(Constants.PREF_IS_ME, isChecked).commit();
                    }
                });

        mCbSlidingMenu.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                    {
                        mActivity.getPreferences().edit().putBoolean(Constants.PREF_IS_SLIDING_MENU, isChecked).commit();
                    }
                });
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
                case R.id.linear_auto_login_container://
                    autoLogin();
                    break;
                case R.id.linear_online_container://
                    onLine();
                    break;
                case R.id.linear_me_container:
                    me();
                    break;
                case R.id.linear_slidingmenu_container:
                    slidingMenu();
                    break;
            }
        }
    }

    private void onLine()
    {
        boolean isAutoLogin = mCbOnLine.isChecked();
        mCbOnLine.setChecked(!isAutoLogin);
        mActivity.getPreferences().edit().putBoolean(Constants.PREF_IS_AUTO_LOGIN, mCbOnLine.isChecked()).commit();
    }

    private void autoLogin()
    {
        boolean isAutoLogin = mCbAutoLogin.isChecked();
        mCbAutoLogin.setChecked(!isAutoLogin);
        mActivity.getPreferences().edit().putBoolean(Constants.PREF_IS_AUTO_LOGIN, mCbAutoLogin.isChecked()).commit();
    }
    private void me()
    {
        boolean isMe = mCbMe.isChecked();
        mCbMe.setChecked(!isMe);
        mActivity.getPreferences().edit().putBoolean(Constants.PREF_IS_ME, mCbMe.isChecked()).commit();
    }

    private void slidingMenu()
    {
        boolean isSldingMenu = mCbSlidingMenu.isChecked();
        mCbSlidingMenu.setChecked(!isSldingMenu);
        mActivity.getPreferences().edit().putBoolean(Constants.PREF_IS_SLIDING_MENU, mCbSlidingMenu.isChecked()).commit();
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
        new Handler(Looper.getMainLooper()).postDelayed(
                new Runnable()
                {
                    @Override
                    public void run()
                    {
                        mActivity.dismissProgressDialog();
                        mActivity.showToast("清除成功");
                    }
                }, 1200L);
    }

}
