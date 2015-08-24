package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.UserCounselListFragment;
import com.xinheng.fragment.UserReportListFragment;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：我的咨询
 */
public class UserCounselActivity extends BaseActivity
{
    private ListView mListView;
    
    public static void actionUserCounsel(BaseActivity activity)
    {
        Intent intent = new Intent(activity, UserCounselActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_common);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, UserCounselListFragment.newInstance()).commit();

    }

  
    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_user_counsel);
    }
}
