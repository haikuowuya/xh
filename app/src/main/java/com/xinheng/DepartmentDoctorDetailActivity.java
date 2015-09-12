package com.xinheng;

import android.content.Intent;
import android.os.Bundle;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.DepartmentDoctorDetailFragment;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：科室医生详情
 */
public class DepartmentDoctorDetailActivity extends BaseActivity
{
    public static void actionDepartDoctorDetail(BaseActivity activity )
    {
        Intent intent = new Intent(activity, DepartmentDoctorDetailActivity.class);
        activity.startActivity(intent);
    }



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_activity_common);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, DepartmentDoctorDetailFragment.newInstance()).commit();
    }

    @Override
    public CharSequence getActivityTitle()
    {
          return  getString(R.string.tv_activity_department_doctor_detail);
    }
}
