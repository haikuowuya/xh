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
import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  我的病历列表
 */
public class UserMedicalListFragment extends PTRListFragment implements DataView
{
    private static final String DATA = UserMedicalItem.DEBUG_SUCCESS;

    public static UserMedicalListFragment newInstance()
    {
        UserMedicalListFragment fragment = new UserMedicalListFragment();
        return fragment;
    }

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
    protected void doGetData()
    {
        mActivity.showProgressDialog();
        RequestUtils.getDataFromUrl(mActivity, "http://www.baidu.com", this);
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem)
    {
        Type type = new TypeToken<List<UserMedicalItem>>()
        {
        }.getType();
        List<UserMedicalItem> items = GsonUtils.jsonToResultItemToList(DATA, type);
        getListView().setAdapter(new UserMedicalListAdapter(mActivity, items));
    }

    @Override
    public void onGetDataFailured(String msg)
    {

    }
}
