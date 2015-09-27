package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.xinheng.AutoCheckActivity;
import com.xinheng.DepartmentNavActivity;
import com.xinheng.OnlineActivity;
import com.xinheng.R;
import com.xinheng.adapter.main.AdPagerAdapter;
import com.xinheng.adapter.main.GridAdapter;
import com.xinheng.base.BaseAdapter;
import com.xinheng.base.BaseFragment;
import com.xinheng.mvp.model.AdItem;
import com.xinheng.mvp.model.HomeGridItem;
import com.xinheng.view.CustomGridView;
import com.xinheng.view.InfiniteViewPagerIndicatorView;

import java.util.LinkedList;
import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/17 0017
 * 时间： 17:38
 * 说明： 首页内容
 */
public class MainFragment extends BaseFragment
{
    private InfiniteViewPagerIndicatorView mInfiniteViewPagerIndicatorView;
    private ScrollView mScrollView;
    private CustomGridView mCustomGridView;

    public static MainFragment newInstance()
    {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_main, null);//TODO
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        mInfiniteViewPagerIndicatorView = (InfiniteViewPagerIndicatorView) view.findViewById(R.id.infinite_viewpager);
        mInfiniteViewPagerIndicatorView.requestDisallowInterceptTouchEvent(true);
        mScrollView = (ScrollView) view.findViewById(R.id.sv_scrollview);
        mCustomGridView = (CustomGridView) view.findViewById(R.id.custom_gridview);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mInfiniteViewPagerIndicatorView.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
        layoutParams.height = (int) getResources().getDimension(R.dimen.dimen_home_ad_height);
        mInfiniteViewPagerIndicatorView.setLayoutParams(layoutParams);
        mInfiniteViewPagerIndicatorView.setViewPagerAdapter(genAdapter());
        mInfiniteViewPagerIndicatorView.startAutoCycle();
        mCustomGridView.setAdapter(genGridAdapter());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mCustomGridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (position == 0)
                {
                    AutoCheckActivity.actionAutoCheck(mActivity);
                }
                else if (position == 1)
                {
                    DepartmentNavActivity.actionDepartmentNav(mActivity);
                }
                else if (position == 3)
                {
                    OnlineActivity.actionOnline(mActivity);
                }
            }
        });
    }

    private BaseAdapter genGridAdapter()
    {
        List<HomeGridItem> data = new LinkedList<>();
        data.add(new HomeGridItem(R.mipmap.ic_main_0, "症状自测"));
        data.add(new HomeGridItem(R.mipmap.ic_main_1, "预约挂号"));
        data.add(new HomeGridItem(R.mipmap.ic_main_2, "便捷检查"));
        data.add(new HomeGridItem(R.mipmap.ic_main_3, "在线售药"));
        data.add(new HomeGridItem(R.mipmap.ic_main_4, "安心用药"));
        data.add(new HomeGridItem(R.mipmap.ic_main_5, "疾病预防"));
        data.add(new HomeGridItem(R.mipmap.ic_main_6, "就医指南"));
        data.add(new HomeGridItem(R.mipmap.ic_main_7, "健康咨询"));
        data.add(new HomeGridItem(R.mipmap.ic_main_8, "医患交流"));
        GridAdapter gridAdapter = new GridAdapter(mActivity, data);
        return gridAdapter;
    }

    public boolean canDoRefresh()
    {
//   return  mScrollView.getScrollY() == 0;
        return false;
    }

    private PagerAdapter genAdapter()
    {
        List<AdItem> data = new LinkedList<>();
        data.add(new AdItem(null));
        data.add(new AdItem(null));
        data.add(new AdItem(null));
        data.add(new AdItem(null));
        AdPagerAdapter adPagerAdapter = new AdPagerAdapter(mActivity, data);
        return adPagerAdapter;
    }

    @Override
    public String getFragmentTitle()
    {
        return getString(R.string.tv_fragment_main);
    }
}
