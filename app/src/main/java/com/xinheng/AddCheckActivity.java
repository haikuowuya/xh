package com.xinheng;

import android.content.Intent;
import android.os.Bundle;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.AddCheckFragment;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：便捷检查(添加检查)界面
 */
public class AddCheckActivity extends BaseActivity
{
    public static final String EXTRA_IS_FROM_CHECK_LIST="is_from_check_list";
    public static void actionAddCheck(BaseActivity activity)
    {
        Intent intent = new Intent(activity, AddCheckActivity.class);
        activity.startActivity(intent);
    }
    public static void actionAddCheck(BaseActivity activity,boolean isFromCheckList)
    {
        Intent intent = new Intent(activity, AddCheckActivity.class);
        intent.putExtra(EXTRA_IS_FROM_CHECK_LIST, isFromCheckList);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_common);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, AddCheckFragment.newInstance()).commit();
    }
    public  boolean isFromCheckList()
    {
        return  getIntent().getBooleanExtra(EXTRA_IS_FROM_CHECK_LIST, false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_add_check);
    }
}
