package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.UserAppointmentAddDetailFragment;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：我的预约加号详情
 */
public class UserAppointmentAddDetailActivity extends UserBaseActivity
{
    public static void actionUserAppointmentAddDetail(BaseActivity activity, String appointmentId)
    {
        Intent intent = new Intent(activity, UserAppointmentAddDetailActivity.class);
        intent.putExtra(EXTRA_ID, appointmentId);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        String appointmentId = getIntent().getStringExtra(EXTRA_ID);
        if (!TextUtils.isEmpty(appointmentId))
        {
            setContentView(R.layout.activity_activity_common);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, UserAppointmentAddDetailFragment.newInstance(appointmentId)).commit();
        }
        else
        {
            mActivity.showCroutonToast("预约id为空， 非法操作");
            mActivity.finish();
        }
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_user_appointment_add_detail);
    }
}
