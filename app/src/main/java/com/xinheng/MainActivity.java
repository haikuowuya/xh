package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.MainFragment;
import com.xinheng.util.Constants;

public class MainActivity extends BaseActivity
{
    public static void actioMain(BaseActivity activity)
    {
        Intent intent = new Intent(activity, MainActivity.class);
        if(activity.getPreferences().getBoolean(Constants.PREF_IS_ONLINE,true))
        {
            intent = new Intent(activity, OnlineActivity.class);
        }
        activity.startActivity(intent);
        activity.finish();
    }

    private MainFragment mMainFragment;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configTitleLayout();
        mMainFragment = MainFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, mMainFragment).commit();
    }

    private void configTitleLayout()
    {
        getCenterTitleView().setVisibility(View.GONE);
        findViewById(R.id.iv_home_logo).setVisibility(View.VISIBLE);

    }

    @Override
    public boolean canDoRefresh()
    {
        return mMainFragment.canDoRefresh();
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_main);
    }
}
