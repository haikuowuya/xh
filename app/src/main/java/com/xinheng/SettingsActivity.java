package com.xinheng;

import android.content.Intent;
import android.os.Bundle;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.SettingsFragment;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：系统设置界面
 */
public class SettingsActivity extends BaseActivity
{
    public static void actionSettings(BaseActivity activity)
    {
        Intent intent = new Intent(activity, SettingsActivity.class);
        activity.startActivity(intent);
    }

    private SettingsFragment mSettingsFragment;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_common);
        mSettingsFragment = SettingsFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, mSettingsFragment).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        mSettingsFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_settings);
    }
}
