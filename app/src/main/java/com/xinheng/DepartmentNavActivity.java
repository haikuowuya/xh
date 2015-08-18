package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xinheng.base.BaseActivity;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：
 */
public class DepartmentNavActivity extends BaseActivity
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
        setContentView(R.layout.activity_department_nav);
        initView();
        mLeftListView.setAdapter(ArrayAdapter.createFromResource(mActivity, R.array.array_menu, android.R.layout.simple_list_item_activated_1));
        mRightListView.setAdapter(ArrayAdapter.createFromResource(mActivity, R.array.array_menu, android.R.layout.simple_list_item_activated_1));
        mLeftListView.setItemChecked(0, true);
    }

    private void initView()
    {
        mLeftListView = (ListView) findViewById(R.id.lv_left_listview);
        mRightListView = (ListView) findViewById(R.id.lv_right_listview);
        mLeftListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_department_nav);
    }
}
