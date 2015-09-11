package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.UserSubscribeListFragment;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：我的预约
 */
public class UserSubscribeActivity extends TabViewPagerActivity
{
    public static void actionUserSubscribe(BaseActivity activity)
    {
        Intent intent = new Intent(activity, UserSubscribeActivity.class);
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
        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public Fragment getItem(int position)
            {
                String type = UserSubscribeListFragment.TYPE_0;
                if(position ==1)
                {
                    type = UserSubscribeListFragment.TYPE_1;
                }
                return UserSubscribeListFragment.newInstance(type);
            }

            @Override
            public int getCount()
            {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position)
            {
                String title = "预约挂号";
                if (position == 1)
                {
                    title = "预约加号";
                }
                return title;
            }
        };

        return pagerAdapter;
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_user_subscribe);
    }
}
