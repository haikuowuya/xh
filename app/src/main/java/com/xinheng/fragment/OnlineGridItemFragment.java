package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.reflect.TypeToken;
import com.xinheng.R;
import com.xinheng.SearchDrugResultActivity;
import com.xinheng.adapter.online.OnlineBottomGridAdapter;
import com.xinheng.base.BaseAdapter;
import com.xinheng.base.BaseFragment;
import com.xinheng.mvp.model.online.HomeOnLineItem;
import com.xinheng.util.GsonUtils;
import com.xinheng.view.CustomGridView;

import java.lang.reflect.Type;
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
    public static final String ARG_ITM_LIST_STRING = "item_list_string";

    public static OnlineGridItemFragment newInstance(String adItemListString)
    {
        OnlineGridItemFragment fragment = new OnlineGridItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_ITM_LIST_STRING, adItemListString);
        fragment.setArguments(bundle);
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
        String adJsonArrayString = getArguments().getString(ARG_ITM_LIST_STRING);
        if (!TextUtils.isEmpty(adJsonArrayString))
        {
            Type type = new TypeToken<List<HomeOnLineItem.Item>>()
            {
            }.getType();
            List<HomeOnLineItem.Item> adItems = GsonUtils.jsonToList(adJsonArrayString, type);
            mCustomGridView.setNumColumns(adItems.size() / 2);
            mCustomGridView.setAdapter(genGridAdapter(adItems));
            mCustomGridView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            HomeOnLineItem.Item item = (HomeOnLineItem.Item) parent.getAdapter().getItem(position);
                            SearchDrugResultActivity.actionSearchDrugResult(mActivity, item.title);
                        }
                    });
        }
    }

    private BaseAdapter genGridAdapter(List<HomeOnLineItem.Item> adItems)
    {
        OnlineBottomGridAdapter gridAdapter = new OnlineBottomGridAdapter(mActivity, adItems);
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
