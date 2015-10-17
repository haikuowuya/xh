package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.PTRListFragment;
import com.xinheng.view.TabViewPagerIndicator;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 18:02
 * 说明：
 */
public class TabViewPagerActivity extends UserBaseActivity
{
    private TabViewPagerIndicator mTabViewPagerIndicator;

    public static void actionTabViewPager(BaseActivity activity)
    {
        Intent intent = new Intent(activity, TabViewPagerActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_viewpager);
        initView();
        mTabViewPagerIndicator.setViewPagerAdapter(genPagerAdapter());
        mTabViewPagerIndicator.getIndicator().setVisibility(View.GONE);
    }

    public TabViewPagerIndicator getTabViewPagerIndicator()
    {
        return mTabViewPagerIndicator;
    }

    private PagerAdapter genPagerAdapter()
    {
        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public Fragment getItem(int position)
            {
                return PTRListFragment.newInstance();
            }

            @Override
            public int getCount()
            {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position)
            {
                return "TAB "+(1+position);
            }
        };

        return pagerAdapter;
    }

    private void initView()
    {
        mTabViewPagerIndicator = (TabViewPagerIndicator) findViewById(R.id.tab_viewpager_indicator);
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_main);
    }
}
