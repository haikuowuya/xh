package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.UserOrderFragment;
import com.xinheng.util.SplitUtils;

import java.util.LinkedList;

/**
 * 作者： libo
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
        getTabViewPagerIndicator().setViewPagerAdapter(genPagerAdapter());
    }

    /**
     * 根据{@link com.xinheng.R.array.array_order}生成订单适配器
     * @return
     */
    private PagerAdapter genPagerAdapter()
    {
        String[] strings = getResources().getStringArray(R.array.array_order);
        final LinkedList<TextOrderStatus> textOrderStatuses = new LinkedList<>();
        for (int i = 0; i < strings.length; i++)
        {
            String[] tmp = SplitUtils.split(strings[i]);
            if (null != tmp && tmp.length > 0 && tmp.length == 2)
            {
                TextOrderStatus textOrderStatus = new TextOrderStatus();
                textOrderStatus.title = tmp[0];
                textOrderStatus.status = tmp[1];
                textOrderStatuses.add(textOrderStatus);
            }
        }

        FragmentPagerAdapter pagerAdapter = null;
        if (!textOrderStatuses.isEmpty())
        {
            pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
            {
                @Override
                public Fragment getItem(int position)
                {
                    return UserOrderFragment.newInstance(textOrderStatuses.get(position).status);
                }

                @Override
                public int getCount()
                {
                    return textOrderStatuses.size();
                }

                @Override
                public CharSequence getPageTitle(int position)
                {
                    return textOrderStatuses.get(position).title;
                }
            };
        }
        return pagerAdapter;
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_user_order);
    }

    public static class TextOrderStatus
    {
        /**
         * 标题
         */
        public String title;
        /**
         * 订单状态
         */
        public String status;
    }
}
