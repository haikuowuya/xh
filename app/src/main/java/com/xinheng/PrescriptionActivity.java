package com.xinheng;

import android.content.Intent;
import android.os.Bundle;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.PrescriptionFragment;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：按方抓药页面
 */
public class PrescriptionActivity extends BaseActivity
{
    public static void actionGetMedical(BaseActivity activity)
    {
        Intent intent = new Intent(activity, PrescriptionActivity.class);
        activity.startActivity(intent);
    }

    private  PrescriptionFragment mPrescriptionFragment;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_activity_common);
        mPrescriptionFragment =    PrescriptionFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, mPrescriptionFragment).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        mPrescriptionFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_get_medical);
    }
}
