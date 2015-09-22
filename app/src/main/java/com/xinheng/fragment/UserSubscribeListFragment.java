package com.xinheng.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import com.google.gson.reflect.TypeToken;
import com.xinheng.R;
import com.xinheng.adapter.user.SubscribeAdapter;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.user.UserSubscribeItem;
import com.xinheng.mvp.presenter.UserSubscribePresenter;
import com.xinheng.mvp.presenter.impl.UserSubscribePresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.DensityUtils;
import com.xinheng.util.GsonUtils;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  我的预约列表
 */
public class UserSubscribeListFragment extends PTRListFragment implements DataView
{
    private static final String DATA = UserSubscribeItem.DEBUG_SUCCESS;

    public static final String TYPE_0 = "0";
    public static final String TYPE_1 = "1";
    public static final String ARG_TYPE = "type";

    public static UserSubscribeListFragment newInstance(String type)
    {
        UserSubscribeListFragment fragment = new UserSubscribeListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    private LinkedList<UserSubscribeItem> mUserSubscribeItems = new LinkedList<>();
    private SubscribeAdapter mSubscribeAdapter;
    /***
     * 预约类型， 0表示预约挂号 1表示预约加号
     */
    private String mType = TYPE_0;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        if (null != getArguments().getString(ARG_TYPE))
        {
            mType = getArguments().getString(ARG_TYPE);
        }
        getListView().setAdapter(ArrayAdapter.createFromResource(mActivity, R.array.array_menu, android.R.layout.simple_list_item_activated_1));
        getListView().setSelector(new ColorDrawable(0x00000000));
        getListView().setDividerHeight(0);
        getListView().setBackgroundColor(0xFFF0F0F0);
        int leftRight = DensityUtils.dpToPx(mActivity, 10.f);
        getListView().setPadding(leftRight, 0,leftRight,0);
        getListView().setDivider(new ColorDrawable(0x00000000));
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
        UserSubscribePresenter userSubscribePresenter = new UserSubscribePresenterImpl(mActivity, this);
        userSubscribePresenter.doGetUserSubscribe(mType);
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem,String requestTag)
    {
        refreshComplete();
        if (null != resultItem)
        {
            mActivity.showCroutonToast(resultItem.message);
            if (resultItem.success())
            {
                Type type = new TypeToken<List<UserSubscribeItem>>()
                {
                }.getType();
                List<UserSubscribeItem> items = GsonUtils.jsonToResultItemToList(GsonUtils.toJson(resultItem), type);
                mUserSubscribeItems.addAll(items);
                if (null == mSubscribeAdapter)
                {
                    mSubscribeAdapter = new SubscribeAdapter(mActivity, mUserSubscribeItems);
                    getListView().setAdapter(mSubscribeAdapter);
                }
                else
                {
                    mSubscribeAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onGetDataFailured(String msg,String requestTag)
    {
      mActivity.showCroutonToast(msg);
    }
}
