package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.reflect.TypeToken;
import com.xinheng.DepartmentDoctorDetailActivity;
import com.xinheng.R;
import com.xinheng.adapter.user.UserDoctorListAdapter;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.depart.DepartDoctorItem;
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
    }

    @Override
    public String getFragmentTitle()
    {
        return getString(R.string.tv_fragment_ptr_list);
    }

    @Override
    protected void doRefresh()
    {
        mUserDoctorItems.clear();
        doGetData();
    }

    @Override
    protected void doGetData()
    {
        UserDoctorPresenter userDoctorPresenter = new UserDoctorPresenterImpl(mActivity, this);
        userDoctorPresenter.doGetUserDoctor();
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem, String requestTag)
    {
        refreshComplete();
        if (null != resultItem)
        {
            mActivity.showToast(resultItem.message);
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
                    getListView().setOnItemClickListener(new OnItemClickListenerImpl());
                } else
                {
                    mUserDoctorListAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag)
    {

    }

    private class OnItemClickListenerImpl implements AdapterView.OnItemClickListener
    {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            UserDoctorItem userDoctorItem = (UserDoctorItem) parent.getAdapter().getItem(position);
            DepartDoctorItem item = new DepartDoctorItem();
            item.img = userDoctorItem.photo;
            item.doctId = userDoctorItem.doctId;
            item.doctName = userDoctorItem.doctName;

            DepartmentDoctorDetailActivity.actionDepartDoctorDetail(mActivity, item);
        }
    }
}
