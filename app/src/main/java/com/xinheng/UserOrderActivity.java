package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.UserOrderListFragment;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：我的订单
 */
public class UserOrderActivity extends TabViewPagerActivity
{
    public static void actionUserOrder(BaseActivity activity)
    {
        Intent intent = new Intent(activity, UserOrderActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setIvRightVisibility(View.VISIBLE);
        getTabViewPagerIndicator().setViewPagerAdapter(genPagerAdapter());
    }

    private PagerAdapter genPagerAdapter()
    {
        final String[] titles = getResources().getStringArray(R.array.array_order);
        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public Fragment getItem(int position)
            {
                return UserOrderListFragment.newInstance( );
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
        };
        return pagerAdapter;
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_user_order);
    }
}
