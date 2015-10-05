package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.AutoCheckFragment;
import com.xinheng.fragment.AutoCheckFragment2;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：症状自测
 */
public class AutoCheckActivity extends TabViewPagerActivity
{
    public static void actionAutoCheck(BaseActivity activity)
    {
        Intent intent = new Intent(activity, AutoCheckActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getTabViewPagerIndicator().setViewPagerAdapter(genPagerAdapter());
    }

    private PagerAdapter genPagerAdapter()
    {
        FragmentPagerAdapter pagerAdapter = null;

        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public Fragment getItem(int position)
            {
                if (position == 0)
                {
                    return AutoCheckFragment.newInstance();
                }
                return AutoCheckFragment2.newInstance();
            }

            @Override
            public int getCount()
            {
                return 1;
            }

            @Override
            public CharSequence getPageTitle(int position)
            {
                String title = "智能导诊";
                if (position == 1)
                {
                    title = "自述导航";
                }
                return title;
            }
        };

        return pagerAdapter;
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_auto_check);
    }
}
