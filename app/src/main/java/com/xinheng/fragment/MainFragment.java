package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.xinheng.R;
import com.xinheng.adapter.main.AdPagerAdapter;
import com.xinheng.adapter.main.GridAdapter;
import com.xinheng.base.BaseAdapter;
import com.xinheng.base.BaseFragment;
import com.xinheng.mvp.model.AdItem;
import com.xinheng.mvp.model.HomeGridItem;
import com.xinheng.util.DensityUtils;
import com.xinheng.view.CustomGridView;
import com.xinheng.view.InfiniteViewPagerIndicatorView;

import java.util.LinkedList;
import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/17 0017
 * 时间： 17:38
 * 说明：
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
        View view = inflater.inflate(R.layout.fragment_main, null);
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        mInfiniteViewPagerIndicatorView = (InfiniteViewPagerIndicatorView) view.findViewById(R.id.infinite_viewpager);
        mScrollView = (ScrollView) view.findViewById(R.id.sv_scrollview);
        mCustomGridView = (CustomGridView) view.findViewById(R.id.custom_gridview);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mInfiniteViewPagerIndicatorView.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
        layoutParams.height = DensityUtils.getScreenWidthInPx(mActivity) * 3 / 5;
        mInfiniteViewPagerIndicatorView.setLayoutParams(layoutParams);
        mInfiniteViewPagerIndicatorView.setBackgroundColor(0xFFFF0000);
        mInfiniteViewPagerIndicatorView.setViewPagerAdapter(genAdapter());
        mCustomGridView.setAdapter(genGridAdapter());
    }

    private BaseAdapter genGridAdapter()
    {
        List<HomeGridItem> data = new LinkedList<>();
        for (int i = 0; i < 8; i++)
        {
            data.add(new HomeGridItem(R.mipmap.ic_launcher, "栏目 " + (1 + i)));
        }
        GridAdapter gridAdapter = new GridAdapter(mActivity, data);
        return gridAdapter;
    }

    public boolean canDoRefresh()
    {
        return false;//mScrollView.getScrollY() == 0;
    }

    private PagerAdapter genAdapter()
    {
        List<AdItem> data = new LinkedList<>();
        data.add(new AdItem("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2832113025,1191479993&fm=80"));
        data.add(new AdItem("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2898205578,742300433&fm=80"));
        data.add(new AdItem("https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=396845035,2757297576&fm=80"));
        data.add(new AdItem("https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=947584711,2775882058&fm=80"));
        AdPagerAdapter adPagerAdapter = new AdPagerAdapter(mActivity, data);
        return adPagerAdapter;
    }

    @Override
    public String getFragmentTitle()
    {
        return getString(R.string.tv_fragment_main);
    }
}
