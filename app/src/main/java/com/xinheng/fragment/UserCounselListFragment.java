package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import com.google.gson.reflect.TypeToken;
import com.xinheng.R;
import com.xinheng.adapter.user.UserCounselListAdapter;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.UserCounselItem;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  我的咨询列表
 */
public class UserCounselListFragment extends PTRListFragment implements DataView
{
    private static final String DATA = UserCounselItem.DEBUG_SUCCESS;

    public static UserCounselListFragment newInstance()
    {
        UserCounselListFragment fragment = new UserCounselListFragment();
        return fragment;
    }

    private LinkedList<UserCounselItem> mUserCounselItems = new LinkedList<>();
    private UserCounselListAdapter mUserCounselListAdapter;

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
        doGetData(); ;
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
        Type type = new TypeToken<List<UserCounselItem>>()
        {
        }.getType();
        List<UserCounselItem> items = GsonUtils.jsonToResultItemToList(DATA, type);
        if (null != items)
        {
            mUserCounselItems.addAll(items);
            if (null == mUserCounselListAdapter)
            {
                mUserCounselListAdapter = new UserCounselListAdapter(mActivity, mUserCounselItems);
                getListView().setAdapter(mUserCounselListAdapter);
            } else
            {
                mUserCounselListAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onGetDataFailured(String msg)
    {

    }
}