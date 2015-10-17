package com.xinheng;

import android.content.Intent;
import android.os.Bundle;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.AccountSecurityFragment;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：账户安全
 */
public class AccountSecurityActivity extends UserBaseActivity
{
    public static void actionAccountSecurity(BaseActivity activity)
    {
        Intent intent = new Intent(activity, AccountSecurityActivity.class);
        activity.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_common);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, AccountSecurityFragment.newInstance()).commit();
    }


    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_account_security);
    }
}
