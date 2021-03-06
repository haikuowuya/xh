package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xinheng.base.BaseActivity;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明： 按科室找药界面
 */
public class DepartmentFindMedicalActivity extends BaseActivity
{
    private ListView mLeftListView;
    private ListView mRightListView;

    public static void actionDeptFindMedical(BaseActivity activity)
    {
        Intent intent = new Intent(activity, DepartmentFindMedicalActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_find_medical);//TODO
        initView();
        setIvRightVisibility(View.VISIBLE);
        mLeftListView.setAdapter(ArrayAdapter.createFromResource(mActivity, R.array.array_department_left, R.layout.list_department_left_item));
        mRightListView.setAdapter(ArrayAdapter.createFromResource(mActivity, R.array.array_department_right,  R.layout.list_department_right_item ));
        mLeftListView.setItemChecked(0, true);
        mRightListView.setItemChecked(0, true);
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
        return getString(R.string.tv_activity_dept_find_medical);
    }
}
