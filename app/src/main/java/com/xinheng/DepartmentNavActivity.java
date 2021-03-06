package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.xinheng.adapter.depart.DepartLeftListAdapter;
import com.xinheng.adapter.depart.DepartRightListAdapter;
import com.xinheng.base.BaseActivity;
import com.xinheng.mvp.model.DepartItem;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.presenter.DepartmentNavPresenter;
import com.xinheng.mvp.presenter.impl.DepartmentNavPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.ACache;
import com.xinheng.util.GsonUtils;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：    首页预约挂号点击进入的界面(科室导航)
 */
public class DepartmentNavActivity extends BaseActivity implements DataView
{
    private ListView mLeftListView;
    private ListView mRightListView;

    public static void actionDepartmentNav(BaseActivity activity)
    {
        Intent intent = new Intent(activity, DepartmentNavActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_nav);//TODO
        initView();
        setIvRightVisibility(View.VISIBLE);
//        mLeftListView.setAdapter(ArrayAdapter.createFromResource(mActivity, R.array.array_department_left, R.layout.list_department_left_item));
//        mRightListView.setAdapter(ArrayAdapter.createFromResource(mActivity, R.array.array_department_right, R.layout.list_department_right_item));
//        mLeftListView.setItemChecked(0, true);
//        mRightListView.setItemChecked(0, true);
        String cacheJson = ACache.get(mActivity).getAsString(mActivity.getActivityTitle().toString());
        if (!TextUtils.isEmpty(cacheJson))
        {
            ResultItem resultItem = GsonUtils.jsonToClass(cacheJson, ResultItem.class);
            if(null != resultItem)
            {
                showData(resultItem);
            }
        }
        DepartmentNavPresenter departmentNavPresenter = new DepartmentNavPresenterImpl(mActivity, this);
        departmentNavPresenter.doGetDepartmentNav();
    }

    private void initView()
    {
        mLeftListView = (ListView) findViewById(R.id.lv_left_listview);
        mRightListView = (ListView) findViewById(R.id.lv_right_listview);
        mLeftListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        mRightListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_department_nav);
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag)
    {

    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem, String requestTag)
    {
        if (null != resultItem)
        {
            showToast(resultItem.message);
            if (resultItem.success())
            {
                showData(resultItem);
            }
        }
    }

    private void showData(ResultItem resultItem)
    {
        Type type = new TypeToken<List<DepartItem>>()
        {
        }.getType();
        String json = GsonUtils.toJson(resultItem);
        List<DepartItem> items = GsonUtils.jsonToResultItemToList(json, type);
        if (null != items && !items.isEmpty())
        {
            ACache.get(mActivity).put(mActivity.getActivityTitle().toString(), json);
            mLeftListView.setAdapter(new DepartLeftListAdapter(mActivity, items));
            mLeftListView.setItemChecked(0, true);
            List<DepartItem> childItems = items.get(0).childs;
            if (null != childItems && !childItems.isEmpty())
            {
                mRightListView.setAdapter(new DepartRightListAdapter(mActivity, childItems));
                mRightListView.setItemChecked(0, true);
            }
            else
            {
                mRightListView.setAdapter(null);
            }
            OnListItemClickListenerImpl onListItemClickListener = new OnListItemClickListenerImpl();
            mLeftListView.setOnItemClickListener(onListItemClickListener);
            mRightListView.setOnItemClickListener(onListItemClickListener);
        }
    }

    private class OnListItemClickListenerImpl implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
        {
            DepartItem departItem = (DepartItem) adapterView.getAdapter().getItem(position);
            if (adapterView == mLeftListView)
            {
                mRightListView.setAdapter(new DepartRightListAdapter(mActivity, departItem.childs));
                mLeftListView.setItemChecked(position, true);
                mRightListView.setItemChecked(0, true);
            }
            else if (adapterView == mRightListView)
            {
                DepartmentDoctorActivity.actionDepartDoctor(mActivity, departItem);
            }
        }
    }

}
