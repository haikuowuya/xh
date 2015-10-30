package com.xinheng.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinheng.BindPhoneActivity;
import com.xinheng.ModifyPwdActivity;
import com.xinheng.R;
import com.xinheng.base.BaseFragment;
import com.xinheng.eventbus.OnBindPhoneModifyEvent;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  账户安全页面
 */
public class AccountSecurityFragment extends BaseFragment
{
    private Bitmap mBitmap;

    public static AccountSecurityFragment newInstance()
    {
        AccountSecurityFragment fragment = new AccountSecurityFragment();
        return fragment;
    }

    /***
     * 登录密码
     */
    private LinearLayout mLinearLoginPwdContainer;

    /***
     * 绑定手机
     */
    private LinearLayout mLinearBindPhoneContainer;

    private TextView mTvPhone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_account_security, null);      //TODO
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        mLinearLoginPwdContainer = (LinearLayout) view.findViewById(R.id.linear_login_pwd_container);
        mLinearBindPhoneContainer = (LinearLayout) view.findViewById(R.id.linear_bind_phone_container);
        mTvPhone = (TextView) view.findViewById(R.id.tv_phone);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        setListener();
        String mobile = mActivity.getLoginSuccessItem().mobile;
        String tmpMobile =  mobile.substring(0, 3)+"****"+mobile.substring(7);
        mTvPhone.setText(tmpMobile);
    }

    @Subscribe
    public void onEventMainThread(OnBindPhoneModifyEvent event)
    {
        if (null != event && !TextUtils.isEmpty(event.mNewBindPhone))
        {
            String mobile = event.mNewBindPhone;
            if(mobile.length() > 7)
            {
                String tmpMobile =mobile.substring(0, 3) + "****" + mobile.substring(7);
                mTvPhone.setText(tmpMobile);
            }
        }
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void setListener()
    {
        OnClickListenerImpl onClickListener = new OnClickListenerImpl();
        mLinearLoginPwdContainer.setOnClickListener(onClickListener);
        mLinearBindPhoneContainer.setOnClickListener(onClickListener);

    }

    @Override
    public String getFragmentTitle()
    {
        return getString(R.string.tv_fragment_user_account);
    }

    private class OnClickListenerImpl implements View.OnClickListener
    {
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.linear_login_pwd_container:// 登录密码
                    ModifyPwdActivity.actionModifyPwd(mActivity);
                    break;
                case R.id.linear_bind_phone_container://绑定手机
                    BindPhoneActivity.actionBindPhone(mActivity);
                    break;
            }
        }

    }

}