package com.xinheng;

import android.content.Intent;
import android.os.Bundle;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.DepartmentDoctorDetailFragment;
import com.xinheng.mvp.model.depart.DepartDoctorItem;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：科室医生详情
 */
public class DepartmentDoctorDetailActivity extends BaseActivity
{
    public static final String EXTRA_DEPART_DOCTOR_ITEM = "depart_doctor_item";

    public static void actionDepartDoctorDetail(BaseActivity activity, DepartDoctorItem item)
    {
        Intent intent = new Intent(activity, DepartmentDoctorDetailActivity.class);
        intent.putExtra(EXTRA_DEPART_DOCTOR_ITEM, item);
        activity.startActivity(intent);
    }

    private DepartDoctorItem mDepartDoctorItem;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mDepartDoctorItem = getIntent().getSerializableExtra(EXTRA_DEPART_DOCTOR_ITEM) == null ? null : (DepartDoctorItem) getIntent().getSerializableExtra(EXTRA_DEPART_DOCTOR_ITEM);
        if (null != mDepartDoctorItem)
        {
            setContentView(R.layout.activity_activity_common);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, DepartmentDoctorDetailFragment.newInstance(mDepartDoctorItem)).commit();
        }
        else
        {
            finish();
        }
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_department_doctor_detail);
    }
}
