package com.xinheng;

import android.content.Intent;
import android.os.Bundle;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.UserPatientListFragment;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明:常用就诊人列表
 */
public class UserPatientListActivity extends UserBaseActivity
{
    public static final String EXTRA_FROM_SELECT_PATIENT = "from_select_patient";
    public static void actionPatient(BaseActivity activity)
    {
       actionPatient(activity, false);
    }

    public static void actionPatient(BaseActivity activity,boolean fromSelectPatient)
    {
        Intent intent = new Intent(activity, UserPatientListActivity.class);
        intent.putExtra(EXTRA_FROM_SELECT_PATIENT, fromSelectPatient);
        activity.startActivity(intent);
    }
    private boolean mFromSelectPatient;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mFromSelectPatient = getIntent().getBooleanExtra(EXTRA_FROM_SELECT_PATIENT, false);
          setContentView(R.layout.activity_activity_common);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, UserPatientListFragment.newInstance(mFromSelectPatient)).commit();

    }



    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_patient);
    }
}
