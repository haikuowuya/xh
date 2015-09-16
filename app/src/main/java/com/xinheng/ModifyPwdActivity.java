package com.xinheng;

import android.content.Intent;
import android.os.Bundle;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.ModifyPwdFragment;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：修改密码
 */
public class ModifyPwdActivity extends BaseActivity
{
    public static void actionModifyPwd(BaseActivity activity)
    {
        Intent intent = new Intent(activity, ModifyPwdActivity.class);
        activity.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_common);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, ModifyPwdFragment.newInstance()).commit();
    }


    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_modify_pwd);
    }
}
