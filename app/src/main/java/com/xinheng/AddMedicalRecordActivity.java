package com.xinheng;

import android.content.Intent;
import android.os.Bundle;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.AddMedicalRecordFragment;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：添加病历界面
 */
public class AddMedicalRecordActivity extends BaseActivity
{
    public static void actionAddMedicalRecord(BaseActivity activity)
    {
        Intent intent = new Intent(activity, AddMedicalRecordActivity.class);
        activity.startActivity(intent);
    }

    private AddMedicalRecordFragment mAddMedicalRecordFragment;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_common);
        mAddMedicalRecordFragment = AddMedicalRecordFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, mAddMedicalRecordFragment).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        mAddMedicalRecordFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_add_medical_record);
    }
}
