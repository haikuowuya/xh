package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.xinheng.base.BaseActivity;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：我的提醒
 */
public class UserRemindActivity extends BaseActivity
{
    private ListView mListView;
    
    public static void actionUserRemind(BaseActivity activity)
    {
        Intent intent = new Intent(activity, UserRemindActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_remind);//TODO
        initView();
        setIvRightVisibility(View.VISIBLE);
       
    }

    private void initView()
    {
        mListView = (ListView) findViewById(R.id.lv_listview);
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_user_remind);
    }
}
