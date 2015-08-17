package com.xinheng.base;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.xinheng.util.AndroidUtils;
import com.xinheng.util.Constants;

/**
 * Created by raiyi-suzhou on 2015/5/11 0011.
 */
public abstract class BaseFragment extends Fragment implements IFragmentTitle
{
    protected BaseActivity mActivity;
    protected boolean mIsInit = false;
    protected boolean mCanPullToRefresh = false;

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        mActivity = (BaseActivity) activity;
        mCanPullToRefresh = Constants.IMEI.equals(AndroidUtils.getDeviceId(mActivity));
    }

    @Override
    public void onResume()
    {
        super.onResume();
//        MobclickAgent.onPageStart("activity[" + mActivity.getActivityTitle() + "] fragment[" + getFragmentTitle() + "]");
    }

    @Override
    public void onPause()
    {
        super.onPause();
//        MobclickAgent.onPageEnd("activity[" + mActivity.getActivityTitle() + "] fragment[" + getFragmentTitle() + "]");
    }
}
