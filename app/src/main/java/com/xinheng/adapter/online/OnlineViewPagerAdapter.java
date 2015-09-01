package com.xinheng.adapter.online;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xinheng.fragment.OnlineGridItemFragment;
import com.xinheng.mvp.model.online.HomeOnLineItem;

import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/24 0024
 * 时间： 09:53
 * 说明：
 */
public class OnlineViewPagerAdapter extends FragmentPagerAdapter
{

   private  List<HomeOnLineItem.Item> items = null;
    public OnlineViewPagerAdapter(FragmentManager fm, List<HomeOnLineItem.Item> items)
    {
        super(fm);
        this.items = items;
    }
    @Override
    public Fragment getItem(int position)
    {
        return OnlineGridItemFragment.newInstance();
    }

    @Override
    public int getCount()
    {
        return items.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {

        return items.get(position).title;
    }
}
