package com.xinheng.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xinheng.util.AndroidUtils;
import com.xinheng.util.Constants;

/**
 * Created by raiyi-suzhou on 2015/5/11 0011.
 */
public abstract class BaseFragment extends Fragment implements IFragmentTitle
{
    protected BaseActivity mActivity;
    /**
     * 一个标识值， 应该在{@link Fragment#onCreateView(LayoutInflater, ViewGroup, Bundle)}方法中将其修改为true
     */
    protected boolean mIsInit = false;
    protected boolean mCanPullToRefresh = false;
    protected  BaseFragment mFragment;

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        mActivity = (BaseActivity) activity;
        mFragment = this;
        mCanPullToRefresh = Constants.IMEI.equals(AndroidUtils.getIMEI(mActivity));
    }

    @Override
    public void onPause()
    {
        super.onPause();
//        MobclickAgent.onPageEnd("activity[" + mActivity.getActivityTitle() + "] fragment[" + getFragmentTitle() + "]");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        //        MobclickAgent.onPageStart("activity[" + mActivity.getActivityTitle() + "] fragment[" + getFragmentTitle() + "]");
        if (getUserVisibleHint())
        {
            if (mIsInit)
            {
                mIsInit = false;
                doGetData();
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
            if (mIsInit)
            {
                mIsInit = false;
                doGetData();
            }
        }
    }

    /**
     * 在此方法中进行网络请求操作  ,注意懒加载只有在和ViewPager结合使用时才有效的
     * 如果只是一个单纯的Fragment,需要手动去调用此方法
     */
    protected void doGetData()
    {
    }

}
