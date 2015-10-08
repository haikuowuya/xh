package com.xinheng;

import android.content.Intent;
import android.os.Bundle;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.AddOrModifyPatientFragment;
import com.xinheng.mvp.model.user.UserPatientItem;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：添加或者修改就诊人界面
 */
public class AddOrModifyPatientActivity extends BaseActivity
{
    public static void actionAddPatient(BaseActivity activity, UserPatientItem userPatientItem)
    {
        Intent intent = new Intent(activity, AddOrModifyPatientActivity.class);
        intent.putExtra(EXTRA_ITEM ,userPatientItem);
        activity.startActivity(intent);
    }
    private  UserPatientItem mUserPatientItem;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mUserPatientItem = getIntent().getSerializableExtra(EXTRA_ITEM)==null?null: (UserPatientItem) getIntent().getSerializableExtra(EXTRA_ITEM);
        setContentView(R.layout.activity_activity_common);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, AddOrModifyPatientFragment.newInstance(mUserPatientItem)).commit();
    }


    @Override
    public CharSequence getActivityTitle()
    {
        String title =  getString(R.string.tv_activity_add_patient);
        if(mUserPatientItem != null)
        {
            title="修改就诊人信息";
        }
        return  title;
    }
}
