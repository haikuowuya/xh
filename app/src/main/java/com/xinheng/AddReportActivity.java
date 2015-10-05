package com.xinheng;

import android.content.Intent;
import android.os.Bundle;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.AddReportFragment;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：添加报告界面
 */
public class AddReportActivity extends BaseActivity
{
    public static void actionAddReport(BaseActivity activity)
    {
        Intent intent = new Intent(activity, AddReportActivity.class);
        activity.startActivity(intent);
    }

    private AddReportFragment mAddReportFragment;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_common);
        mAddReportFragment = AddReportFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, mAddReportFragment).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        mAddReportFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_add_report);
    }
}
