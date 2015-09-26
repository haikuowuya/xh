package com.xinheng;

import android.content.Intent;
import android.os.Bundle;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.AppointmentAddFragment;
import com.xinheng.mvp.model.doctor.DoctorScheduleItem;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：预约加号界面
 */
public class AppointmentAddActivity extends BaseActivity
{

    public static void actionAppointmentAdd(BaseActivity activity, DoctorScheduleItem doctorScheduleItem)
    {
        Intent intent = new Intent(activity, AppointmentAddActivity.class);
        intent.putExtra(EXTRA_ITEM, doctorScheduleItem);
        activity.startActivity(intent);
    }

    private AppointmentAddFragment mAppointmentAddFragment;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        DoctorScheduleItem doctorScheduleItem = getIntent().getSerializableExtra(EXTRA_ITEM) == null ? null : (DoctorScheduleItem) getIntent().getSerializableExtra(EXTRA_ITEM);
        if (null != doctorScheduleItem)
        {
            setContentView(R.layout.activity_activity_common);
            mAppointmentAddFragment = AppointmentAddFragment.newInstance(doctorScheduleItem);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, mAppointmentAddFragment).commit();
        }
        else
        {
            mActivity.showCroutonToast("医生排班信息为空， 非法操作");
            mActivity.finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != mAppointmentAddFragment)
        {
            mAppointmentAddFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_appointment_add);
    }
}
