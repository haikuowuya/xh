package com.xinheng;

import android.content.Intent;
import android.os.Bundle;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.AppointmentAddFragment;
import com.xinheng.mvp.model.doctor.DoctorDetailItem;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：预约加号界面
 */
public class AppointmentAddActivity extends BaseActivity
{
    public static final String EXTRA_POSITION = "position";

    public static void actionAppointmentAdd(BaseActivity activity, DoctorDetailItem doctorDetailItem, int position)
    {
        Intent intent = new Intent(activity, AppointmentAddActivity.class);
        intent.putExtra(EXTRA_ITEM, doctorDetailItem);
        intent.putExtra(EXTRA_POSITION, position);
        activity.startActivity(intent);
    }

    private AppointmentAddFragment mAppointmentAddFragment;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        DoctorDetailItem doctorDetailItem = getIntent().getSerializableExtra(EXTRA_ITEM) == null ? null : (DoctorDetailItem) getIntent().getSerializableExtra(EXTRA_ITEM);
        int position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        if (null != doctorDetailItem)
        {
            setContentView(R.layout.activity_activity_common);
            mAppointmentAddFragment = AppointmentAddFragment.newInstance(doctorDetailItem, position);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, mAppointmentAddFragment).commit();
        } else
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
