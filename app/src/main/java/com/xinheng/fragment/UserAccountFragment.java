package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xinheng.R;
import com.xinheng.base.BaseFragment;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  我的帐号页面
 */
public class UserAccountFragment extends BaseFragment
{
    public static UserAccountFragment newInstance()
    {
        UserAccountFragment fragment = new UserAccountFragment();
        return fragment;
    }

    private LinearLayout mLinearPhotoContainer;
    /**
     * 姓名
     */
    private LinearLayout mLinearUserName;
    /***
     * 昵称
     */
    private LinearLayout mLinearNick;
    /***
     * 地址管理
     */
    private LinearLayout mLinearAddress;

    /***
     * 账户安全
     */
    private LinearLayout mLinearAccountSecure;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user_account, null);
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        mLinearAccountSecure = (LinearLayout) view.findViewById(R.id.linear_account_secure);
        mLinearAddress = (LinearLayout) view.findViewById(R.id.linear_address_container);
        mLinearNick = (LinearLayout) view.findViewById(R.id.linear_nick_container);
        mLinearUserName = (LinearLayout) view.findViewById(R.id.linear_username_container);
        mLinearPhotoContainer = (LinearLayout) view.findViewById(R.id.linear_photo_container);
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
        mLinearAccountSecure.setOnClickListener(onClickListener);
        mLinearAddress.setOnClickListener(onClickListener);
        mLinearNick.setOnClickListener(onClickListener);
        mLinearUserName.setOnClickListener(onClickListener);
        mLinearPhotoContainer.setOnClickListener(onClickListener);
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
                case R.id.linear_photo_container://上传头像
                    photo();
                    break;
                case R.id.linear_account_secure://账户安全
                    accountSecure();
                    break;
                case R.id.linear_address_container://地址管理
                    address();
                    break;
                case R.id.linear_nick_container://昵称
                    nick();
                    break;
                case R.id.linear_username_container://用户名/姓名
                    username();
                    break;
            }
        }

        /***
         * 上传头像
         */
        private void photo()
        {
            mActivity.showCroutonToast("上传头像");
        }

        /**
         * 账户安全
         */
        private void accountSecure()
        {
            mActivity.showCroutonToast("账户安全");
        }

        /**
         * 地址管理
         */
        private void address()
        {
            mActivity.showCroutonToast("地址管理");
        }

        /**
         * 昵称
         */
        private void nick()
        {
            mActivity.showCroutonToast("昵称");
        }

        /***
         * 姓名
         */
        private void username()
        {
            mActivity.showCroutonToast("姓名");
        }
    }

}
