package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.CityListFragment;
import com.xinheng.mvp.model.city.CityItem;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明: 城市列表选择
 */
public class CityListActivity extends BaseActivity
{
    public static void actionCityList(BaseActivity activity)
    {
        Intent intent = new Intent(activity, CityListActivity.class);
        activity.startActivity(intent);
    }

    private CityListFragment mCityFragment;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_common);
        mCityFragment = CityListFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, mCityFragment).commit();

    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_city_list);
    }

    public void toCity(CityItem cityItem)
    {
        mCityFragment = CityListFragment.newInstance(cityItem);
        getSupportFragmentManager().beginTransaction().add(R.id.frame_content_container, mCityFragment).addToBackStack(null).commit();
        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener()
                {
                    public void onBackStackChanged()
                    {
                        mCityFragment = (CityListFragment) getSupportFragmentManager().findFragmentById(R.id.frame_content_container);
                        if (null != mCityFragment)
                        {

                        }
                    }
                });
    }

    public void toArea(CityItem cityItem, CityItem.AreaItem areaItem)
    {
        mCityFragment = CityListFragment.newInstance(cityItem, areaItem);
        getSupportFragmentManager().beginTransaction().add(R.id.frame_content_container, mCityFragment).addToBackStack(null).commit();
        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener()
                {
                    public void onBackStackChanged()
                    {
                        mCityFragment = (CityListFragment) getSupportFragmentManager().findFragmentById(R.id.frame_content_container);
                        if (null != mCityFragment)
                        {

                        }
                    }
                });
    }

}
