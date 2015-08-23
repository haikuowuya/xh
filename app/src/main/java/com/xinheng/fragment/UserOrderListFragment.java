package com.xinheng.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import com.google.gson.reflect.TypeToken;
import com.xinheng.R;
import com.xinheng.adapter.user.SubscribeAdapter;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.UserSubscribeItem;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  我的订单列表
 */
public class UserOrderListFragment extends PTRListFragment implements DataView
{
    private static final String DATA = UserSubscribeItem.DEBUG_SUCCESS;
 

    public static UserOrderListFragment newInstance()
    {
        UserOrderListFragment fragment = new UserOrderListFragment();
        return fragment;
    }
  

    private LinkedList<UserSubscribeItem> mUserSubscribeItems = new LinkedList<>();
    private SubscribeAdapter mSubscribeAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        getListView().setAdapter(ArrayAdapter.createFromResource(mActivity, R.array.array_menu, android.R.layout.simple_list_item_activated_1));
        getListView().setSelector(new ColorDrawable(0x00000000));
        getListView().setDividerHeight(0);
        getListView().setDivider(new ColorDrawable(0x00000000));
    }

    @Override
    public String getFragmentTitle()
    {
        
        return "我的订单";
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
        Type type = new TypeToken<List<UserSubscribeItem>>()
        {
        }.getType();
        List<UserSubscribeItem> items = GsonUtils.jsonToResultItemToList(DATA, type);
        mUserSubscribeItems.addAll(items);
        if (null == mSubscribeAdapter)
        {
            mSubscribeAdapter = new SubscribeAdapter(mActivity, mUserSubscribeItems);
            getListView().setAdapter(mSubscribeAdapter);
        } else
        {
            mSubscribeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onGetDataFailured(String msg)
    {

    }
}
