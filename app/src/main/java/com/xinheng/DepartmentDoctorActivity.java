package com.xinheng;

import android.content.Intent;
import android.os.Bundle;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.DepartmentDoctorListFragment;
import com.xinheng.mvp.model.DepartItem;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：科室医生列表
 */
public class DepartmentDoctorActivity extends BaseActivity
{
    public static final String EXTRA_DEPAET_ITEM = "departItem";

    public static void actionDepartDoctor(BaseActivity activity, DepartItem departItem)
    {
        Intent intent = new Intent(activity, DepartmentDoctorActivity.class);
        intent.putExtra(EXTRA_DEPAET_ITEM, departItem);
        activity.startActivity(intent);
    }

    private DepartItem mDepartItem;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mDepartItem = getIntent().getSerializableExtra(EXTRA_DEPAET_ITEM) == null ? null : (DepartItem) getIntent().getSerializableExtra(EXTRA_DEPAET_ITEM);
        if (null != mDepartItem)
        {
            setContentView(R.layout.activity_activity_common);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, DepartmentDoctorListFragment.newInstance(mDepartItem)).commit();
        }
        else
        {
            finish();
        }
    }

    @Override
    public CharSequence getActivityTitle()
    {
        String title = getString(R.string.tv_activity_depart_doctor);
        if (null != mDepartItem)
        {
            title = mDepartItem.name;
        }
        return title;
    }
}
