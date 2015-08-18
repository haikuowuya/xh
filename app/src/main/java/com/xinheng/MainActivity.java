package com.xinheng;

import android.os.Bundle;
import android.view.View;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.MainFragment;
import com.xinheng.slidingmenu.SlidingMenu;

public class MainActivity extends BaseActivity
{
    private MainFragment mMainFragment;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configTitleLayout();
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        mMainFragment = MainFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, mMainFragment).commit();
    }

    private void configTitleLayout()
    {
        getCenterTitleView().setVisibility(View.GONE);
        findViewById(R.id.iv_home_logo).setVisibility(View.VISIBLE);
        findViewById(R.id.iv_right).setVisibility(View.VISIBLE);
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
