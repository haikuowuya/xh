package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.UserMedicalDetailFragment;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：我的病历详情页面
 */
public class UserMedicalDetailActivity extends BaseActivity
{
    public static final String  EXTRA_ID="id";
    public static void actionUserMedicalDetail(BaseActivity activity,String medicalId)
    {
        Intent intent = new Intent(activity, UserMedicalDetailActivity.class);
        intent.putExtra(EXTRA_ID, medicalId);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        String medicalId = getIntent().getStringExtra(EXTRA_ID);
        if(!TextUtils.isEmpty(medicalId))
        {
            setContentView(R.layout.activity_activity_common);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, UserMedicalDetailFragment.newInstance(medicalId)).commit();
        }
        else
        {
            showToast("病历id为空，非法操作");
            finish();
        }

    }



    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_user_medical_detail);
    }
}
