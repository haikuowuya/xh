package com.xinheng.adapter.online;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xinheng.fragment.OnlineGridItemFragment;
import com.xinheng.mvp.model.online.OnLineBottomItem;
import com.xinheng.util.GsonUtils;

import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/24 0024
 * 时间： 09:53
 * 说明：
 */
public class OnlineViewPagerAdapter extends FragmentPagerAdapter
{

    private List<OnLineBottomItem> items = null;

    public OnlineViewPagerAdapter(FragmentManager fm, List<OnLineBottomItem> items)
    {
        super(fm);
        this.items = items;
    }

    @Override
    public Fragment getItem(int position)
    {
        return OnlineGridItemFragment.newInstance(GsonUtils.toJson(items.get(position).items));
    }

    @Override
    public int getCount()
    {
        return items.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {

        return items.get(position).name;
    }
}
