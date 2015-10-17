package com.xinheng;

import android.content.Intent;
import android.os.Bundle;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.UserNicknameFragment;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：修改昵称界面
 */
public class UserNicknameActivity extends UserBaseActivity
{
    public static void actionUserNickname(BaseActivity activity)
    {
        Intent intent = new Intent(activity, UserNicknameActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_common);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, UserNicknameFragment.newInstance()).commit();
    }


    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_user_nickname);
    }
}
