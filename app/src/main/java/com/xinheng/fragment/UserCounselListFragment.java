package com.xinheng.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.reflect.TypeToken;
import com.xinheng.R;
import com.xinheng.UserCounselDetailActivity;
import com.xinheng.adapter.user.UserCounselListAdapter;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.user.UserCounselItem;
import com.xinheng.mvp.presenter.UserCounselPresenter;
import com.xinheng.mvp.presenter.impl.UserCounselPresenterImpl;
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
        getListView().setDividerHeight(0);
        getListView().setBackgroundColor(0xFFF0F0F0);
        int leftRight = DensityUtils.dpToPx(mActivity, 10.f);
        getListView().setPadding(leftRight, leftRight, leftRight, 0);
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
        UserCounselPresenter userCounselPresenter = new UserCounselPresenterImpl(mActivity, this);
        userCounselPresenter.doGetUserCounsel();
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem, String requestTag)
    {
        refreshComplete();
        if (null != resultItem)
        {
            mActivity.showToast(resultItem.message);
            if (resultItem.success())
            {
                Type type = new TypeToken<List<UserCounselItem>>()
                {
                }.getType();
                List<UserCounselItem> items = GsonUtils.jsonToResultItemToList(GsonUtils.toJson(resultItem), type);
                if (null != items)
                {
                    mUserCounselItems.clear();
                    mUserCounselItems.addAll(items);
                    if (null == mUserCounselListAdapter)
                    {
                        mUserCounselListAdapter = new UserCounselListAdapter(mActivity, mUserCounselItems);
                        getListView().setAdapter(mUserCounselListAdapter);
                    }
                    else
                    {
                        mUserCounselListAdapter.notifyDataSetChanged();
                    }

                    getListView().setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            UserCounselDetailActivity.actionUserCounselDetail(mActivity, mUserCounselItems.get(position));
                        }
                    });
                }
            }
        }

    }

    @Override
    public void onGetDataFailured(String msg, String requestTag)
    {

    }
}
