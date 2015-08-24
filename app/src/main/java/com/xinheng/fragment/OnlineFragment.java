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

import com.xinheng.R;
import com.xinheng.UserOrderActivity;
import com.xinheng.adapter.main.AdPagerAdapter;
import com.xinheng.adapter.online.OnlineGridAdapter;
import com.xinheng.base.BaseAdapter;
import com.xinheng.base.BaseFragment;
import com.xinheng.mvp.model.AdItem;
import com.xinheng.mvp.model.HomeGridItem;
import com.xinheng.util.DensityUtils;
import com.xinheng.view.CustomGridView;
import com.xinheng.view.InfiniteViewPagerIndicatorView;
import com.xinheng.view.TabViewPagerIndicator;

import java.util.LinkedList;
import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/17 0017
 * 时间： 17:38
 * 说明： 首页内容
 */
public class OnlineFragment extends BaseFragment
{
    /**
     * 顶部的滚动广告位置
     */
    private InfiniteViewPagerIndicatorView mInfiniteViewPagerIndicatorView;
    private ScrollView mScrollView;
    /**
     * 中间的四个功能按钮
     */
    private CustomGridView mCustomGridView;

    /**
     * 功能按钮下面的布局
     */
    private LinearLayout mLinearCenterContainer;
    /**
     * 最下面的左右滑动的布局
     */
    private TabViewPagerIndicator mTabViewPagerIndicator;

    public static OnlineFragment newInstance()
    {
        OnlineFragment fragment = new OnlineFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_online, null);//TODO
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        mInfiniteViewPagerIndicatorView = (InfiniteViewPagerIndicatorView) view.findViewById(R.id.infinite_viewpager);
        mTabViewPagerIndicator = (TabViewPagerIndicator) view.findViewById(R.id.tab_viewpager_online_indicator);
        mLinearCenterContainer = (LinearLayout) view.findViewById(R.id.linear_online_center_container);
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
        fillCenterContainer();
        fillTabViewPagerIndicator();
    }

    private void fillCenterContainer()
    {
        for (int i = 0; i < 2; i++)
        {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_online_center_item, null);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.bottomMargin = DensityUtils.dpToPx(mActivity, 10.f);
            mLinearCenterContainer.addView(view, layoutParams);
        }
    }

    private void fillTabViewPagerIndicator()
    {
     //   mTabViewPagerIndicator.setViewPagerAdapter(genIndicatorAdapter());
    }

    private PagerAdapter genIndicatorAdapter()
    {
        return null;
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
                if (position == 3)//我的订单
                {
                    UserOrderActivity.actionUserOrder(mActivity);
                }
            }
        });
    }

    private BaseAdapter genGridAdapter()
    {
        List<HomeGridItem> data = new LinkedList<>();
        data.add(new HomeGridItem(R.mipmap.ic_online_0, "按方抓药"));
        data.add(new HomeGridItem(R.mipmap.ic_online_1, "轻松找药"));
        data.add(new HomeGridItem(R.mipmap.ic_online_2, "购物车"));
        data.add(new HomeGridItem(R.mipmap.ic_online_3, "我的订单"));
        OnlineGridAdapter gridAdapter = new OnlineGridAdapter(mActivity, data);
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
