package com.xinheng;

import android.content.Intent;
import android.os.Bundle;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.OnLineCounselFragment;
import com.xinheng.mvp.model.doctor.DoctorDetailItem;
import com.xinheng.mvp.model.doctor.DoctorScheduleItem;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：在线咨询界面
 */
public class OnLineCounselActivity extends BaseActivity
{

    public static void actionOnLineCounsel(BaseActivity activity, DoctorDetailItem doctorDetailItem)
    {
        Intent intent = new Intent(activity, OnLineCounselActivity.class);
        intent.putExtra(EXTRA_ITEM, doctorDetailItem);
        activity.startActivity(intent);
    }

    private OnLineCounselFragment mOnLineCounselFragment;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        DoctorDetailItem doctorDetailItem= getIntent().getSerializableExtra(EXTRA_ITEM) == null ? null : (DoctorDetailItem) getIntent().getSerializableExtra(EXTRA_ITEM);
        if (null != doctorDetailItem)
        {
            setContentView(R.layout.activity_activity_common);
            mOnLineCounselFragment = OnLineCounselFragment.newInstance(doctorDetailItem);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, mOnLineCounselFragment).commit();
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
        if (null != mOnLineCounselFragment)
        {
            mOnLineCounselFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_online_counsel);
    }
}
