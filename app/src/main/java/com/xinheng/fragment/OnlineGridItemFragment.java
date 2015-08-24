package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.xinheng.R;
import com.xinheng.adapter.online.OnlineBottomGridAdapter;
import com.xinheng.base.BaseAdapter;
import com.xinheng.base.BaseFragment;
import com.xinheng.mvp.model.HomeGridItem;
import com.xinheng.view.CustomGridView;

import java.util.LinkedList;
import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/17 0017
 * 时间： 17:38
 * 说明： 在线售药 底部的Grid
 */
public class OnlineGridItemFragment extends BaseFragment
{
    private CustomGridView mCustomGridView;

    public static OnlineGridItemFragment newInstance()
    {
        OnlineGridItemFragment fragment = new OnlineGridItemFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mCustomGridView = new CustomGridView(mActivity);
        mCustomGridView.setNumColumns(2);
        mCustomGridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        return mCustomGridView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mCustomGridView.setAdapter(genGridAdapter());
    }

    private BaseAdapter genGridAdapter()
    {
        List<HomeGridItem> data = new LinkedList<>();
        data.add(new HomeGridItem(R.mipmap.ic_online_0, "按方抓药"));
        data.add(new HomeGridItem(R.mipmap.ic_online_1, "轻松找药"));
        data.add(new HomeGridItem(R.mipmap.ic_online_2, "购物车"));
        data.add(new HomeGridItem(R.mipmap.ic_online_3, "我的订单"));
        OnlineBottomGridAdapter gridAdapter = new OnlineBottomGridAdapter(mActivity, data);
        return gridAdapter;
    }

    public boolean canDoRefresh()
    {
        return false;
    }

    @Override
    public String getFragmentTitle()
    {
        return getString(R.string.tv_fragment_main);
    }
}
