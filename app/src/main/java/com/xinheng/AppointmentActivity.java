package com.xinheng;

import android.content.Intent;
import android.os.Bundle;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.AppointmentFragment;
import com.xinheng.mvp.model.doctor.DoctorDetailItem;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：预约挂号界面
 */
public class AppointmentActivity extends BaseActivity
{
    public  static  final  String EXTRA_POSITION="position";
    public static void actionAppointment(BaseActivity activity, DoctorDetailItem doctorDetailItem, int position)
    {
        Intent intent = new Intent(activity, AppointmentActivity.class);
        intent.putExtra(EXTRA_ITEM, doctorDetailItem);
        intent.putExtra(EXTRA_POSITION,position);
        activity.startActivity(intent);
    }

    private AppointmentFragment mAppointmentFragment;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        DoctorDetailItem doctorDetailItem = getIntent().getSerializableExtra(EXTRA_ITEM) ==null?null: (DoctorDetailItem) getIntent().getSerializableExtra(EXTRA_ITEM);
        int position = getIntent().getIntExtra(EXTRA_POSITION,0);
        if ( null != doctorDetailItem)
        {
            setContentView(R.layout.activity_activity_common);
            mAppointmentFragment = AppointmentFragment.newInstance(doctorDetailItem, position);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, mAppointmentFragment).commit();
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
        if(null != mAppointmentFragment)
        {
            mAppointmentFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_appointment);
    }
}
