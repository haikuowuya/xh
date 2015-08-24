package com.xinheng.adapter.online;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xinheng.fragment.OnlineGridItemFragment;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/24 0024
 * 时间： 09:53
 * 说明：
 */
public class OnlineViewPagerAdapter extends FragmentPagerAdapter
{
    private String[] titles;

    public OnlineViewPagerAdapter(FragmentManager fm, String[] titles)
    {
        super(fm);
        this.titles = titles;
    }
    @Override
    public Fragment getItem(int position)
    {
        return OnlineGridItemFragment.newInstance();
    }

    @Override
    public int getCount()
    {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {

        return titles[position];
    }
}
