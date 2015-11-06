package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.xinheng.DrugDetailActivity;
import com.xinheng.R;
import com.xinheng.adapter.drug.SearchDrugResultListAdapter;
import com.xinheng.base.BaseFragment;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.prescription.DrugItem;
import com.xinheng.mvp.presenter.DrugSearchPresenter;
import com.xinheng.mvp.presenter.impl.DrugSearchPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.Constants;
import com.xinheng.util.GsonUtils;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  点击在线售药最底部的药品时，搜索结果药品Fragment页面
 */
public class SearchDrugResultFragment extends BaseFragment implements DataView
{
    public static final String ARG_SEARCH_KEY = "search_key";

    public static SearchDrugResultFragment newInstance(String searchKey)
    {
        SearchDrugResultFragment fragment = new SearchDrugResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_SEARCH_KEY, searchKey);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_search_drug, null);
        initView(view);
        mIsInit = true;
        return view;
    }

    private ListView mListView;

    private List<DrugItem> mDrugItems = new LinkedList<>();
    private List<DrugItem> mSelectedItems = null;
    private SearchDrugResultListAdapter mDrugListAdapter;
    private String mSearchKey;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        setListener();
        mSearchKey = getArguments().getString(ARG_SEARCH_KEY);
        search();
    }

    private void setListener()
    {

    }

    private void initView(View view)
    {
        mListView = (ListView) view.findViewById(R.id.lv_listview);
    }

    @Override
    public String getFragmentTitle()
    {
        return "我的订单";
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem, String requestTag)
    {
        if (resultItem != null)
        {
            mActivity.showToast(resultItem.message);
            if (resultItem.success())
            {
                Type type = new TypeToken<List<DrugItem>>()
                {
                }.getType();
                List<DrugItem> drugItems = GsonUtils.jsonToList(resultItem.properties.getAsJsonObject().get("list").getAsJsonArray().toString(), type);
                mDrugItems.clear();
                mDrugItems.addAll(drugItems);
                showListContentData();
            }
        }
    }

    private void showListContentData()
    {
        if (null != mDrugItems)
        {
            if (null == mDrugListAdapter)
            {
                mDrugListAdapter = new SearchDrugResultListAdapter(mActivity, mDrugItems);
                mListView.setAdapter(mDrugListAdapter);
                mListView.setOnItemClickListener(
                        new AdapterView.OnItemClickListener()
                        {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                            {
                                DrugItem drugItem = (DrugItem) parent.getItemAtPosition(position);
                                DrugDetailActivity.actionDrugDetail(mActivity, drugItem.id);
                            }
                        });
            } else
            {
                mDrugListAdapter.notifyDataSetChanged();
            }

        }
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag)
    {

    }

    private void search()
    {

        DrugSearchPresenter drugSearchPresenter = new DrugSearchPresenterImpl(mActivity, this);
        drugSearchPresenter.doSearchDrug(Constants.HID, mSearchKey);
    }
}
