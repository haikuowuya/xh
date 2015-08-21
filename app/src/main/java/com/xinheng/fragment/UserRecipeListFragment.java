package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import com.google.gson.reflect.TypeToken;
import com.xinheng.R;
import com.xinheng.adapter.user.UserMedicalListAdapter;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.UserMedicalItem;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  我的处方列表
 */
public class UserRecipeListFragment extends PTRListFragment implements DataView
{
    private static final String DATA = UserMedicalItem.DEBUG_SUCCESS;

    public static UserRecipeListFragment newInstance()
    {
        UserRecipeListFragment fragment = new UserRecipeListFragment();
        return fragment;
    }
    private LinkedList<UserMedicalItem> mUserMedicalItems = new LinkedList<>();
    private UserMedicalListAdapter mUserMedicalListAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        getListView().setAdapter(ArrayAdapter.createFromResource(mActivity, R.array.array_menu, android.R.layout.simple_list_item_activated_1));
    }

    @Override
    public String getFragmentTitle()
    {
        return getString(R.string.tv_fragment_ptr_list);
    }

    @Override
    protected void doRefresh()
    {
        doGetData();
    }
    @Override
    protected void doGetData()
    {
        mActivity.showProgressDialog();
        RequestUtils.getDataFromUrl(mActivity, "http://www.baidu.com", this);
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem)
    {
        refreshComplete();
        Type type = new TypeToken<List<UserMedicalItem>>()
        {
        }.getType();
        List<UserMedicalItem> items = GsonUtils.jsonToResultItemToList(DATA, type);
        if (null != items)
        {
            mUserMedicalItems.addAll(items);
            if (null == mUserMedicalListAdapter)
            {
                mUserMedicalListAdapter = new UserMedicalListAdapter(mActivity, mUserMedicalItems);
                getListView().setAdapter(mUserMedicalListAdapter);
            } else
            {
                mUserMedicalListAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onGetDataFailured(String msg)
    {

    }
}
