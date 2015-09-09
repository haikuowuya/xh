package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import com.google.gson.reflect.TypeToken;
import com.xinheng.DepartmentDoctorActivity;
import com.xinheng.R;
import com.xinheng.adapter.depart.DepartDoctorListAdapter;
import com.xinheng.mvp.model.DepartItem;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.depart.DepartDoctorItem;
import com.xinheng.mvp.presenter.DepartmentDoctorPresenter;
import com.xinheng.mvp.presenter.impl.DepartmentDoctorPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  科室医生列表
 */
public class DepartmentDoctorListFragment extends PTRListFragment implements DataView
{
    public static DepartmentDoctorListFragment newInstance(DepartItem departItem)
    {
        DepartmentDoctorListFragment fragment = new DepartmentDoctorListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DepartmentDoctorActivity.EXTRA_DEPAET_ITEM, departItem);
        fragment.setArguments(bundle);
        return fragment;
    }

    private DepartItem mDepartItem;
    private LinkedList<DepartDoctorItem> mDepartDoctorItems = new LinkedList<>();
    private DepartDoctorListAdapter mDepartDoctorListAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mDepartItem = getArguments().getSerializable(DepartmentDoctorActivity.EXTRA_DEPAET_ITEM) == null ? null : (DepartItem) getArguments().getSerializable(DepartmentDoctorActivity.EXTRA_DEPAET_ITEM);
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
        if (null != mDepartItem)
        {
            DepartmentDoctorPresenter departmentDoctorPresenter = new DepartmentDoctorPresenterImpl(mActivity, this);
            departmentDoctorPresenter.doGetDepartDoctorList(mDepartItem.id);
        }
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem)
    {
        refreshComplete();
        if (null != resultItem)
        {
            mActivity.showCroutonToast(resultItem.message);
            if (resultItem.success())
            {
                Type type = new TypeToken<List<DepartDoctorItem>>()
                {
                }.getType();
                List<DepartDoctorItem> items = GsonUtils.jsonToResultItemToList(GsonUtils.toJson(resultItem), type);
                if (null != items)
                {
                    if(items.isEmpty())
                    {
                        items = GsonUtils.jsonToResultItemToList(DepartDoctorItem.DEBUG_SUCCESS, type);
                    }
                    mDepartDoctorItems.addAll(items);
                    if (null == mDepartDoctorListAdapter)
                    {
                        mDepartDoctorListAdapter = new DepartDoctorListAdapter(mActivity, mDepartDoctorItems);
                        getListView().setAdapter(mDepartDoctorListAdapter);
                    }
                    else
                    {
                        mDepartDoctorListAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    @Override
    public void onGetDataFailured(String msg)
    {

    }
}