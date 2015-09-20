package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import com.google.gson.reflect.TypeToken;
import com.xinheng.R;
import com.xinheng.adapter.user.UserDoctorListAdapter;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.user.UserDoctorItem;
import com.xinheng.mvp.presenter.UserDoctorPresenter;
import com.xinheng.mvp.presenter.impl.UserDoctorPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  我的医生列表
 */
public class UserDoctorListFragment extends PTRListFragment implements DataView
{
    private static final String DATA = UserDoctorItem.DEBUG_SUCCESS;

    public static UserDoctorListFragment newInstance()
    {
        UserDoctorListFragment fragment = new UserDoctorListFragment();
        return fragment;
    }

    private LinkedList<UserDoctorItem> mUserDoctorItems = new LinkedList<>();
    private UserDoctorListAdapter mUserDoctorListAdapter;

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
        UserDoctorPresenter userDoctorPresenter = new UserDoctorPresenterImpl(mActivity, this);
        userDoctorPresenter.doGetUserDoctor();
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem)
    {
        refreshComplete();
        if (null != resultItem)
        {
            mActivity.showCroutonToast(resultItem.message);
            Type type = new TypeToken<List<UserDoctorItem>>()
            {
            }.getType();
            List<UserDoctorItem> items = GsonUtils.jsonToResultItemToList(GsonUtils.toJson(resultItem), type);
            if (null != items)
            {
                mUserDoctorItems.addAll(items);
                if (null == mUserDoctorListAdapter)
                {
                    mUserDoctorListAdapter = new UserDoctorListAdapter(mActivity, mUserDoctorItems);
                    getListView().setAdapter(mUserDoctorListAdapter);
                }
                else
                {
                    mUserDoctorListAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onGetDataFailured(String msg)
    {

    }
}
