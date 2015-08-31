package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.OnLineFragment;

public class OnlineActivity extends BaseActivity
{
    private OnLineFragment mOnlineFragment;
    
    public static void actionOnline(BaseActivity activity)
    {
        Intent intent = new Intent(activity, OnlineActivity.class);
        activity.startActivity(intent);
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_common);
        configTitleLayout();
        mOnlineFragment = OnLineFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, mOnlineFragment).commit();
    }

    private void configTitleLayout()
    {
        getCenterTitleView().setVisibility(View.GONE);
        findViewById(R.id.iv_home_logo).setVisibility(View.VISIBLE);
    }

    @Override
    public boolean canDoRefresh()
    {
        return mOnlineFragment.canDoRefresh();
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_online);
    }


}
