package com.xinheng;

import android.content.Intent;
import android.os.Bundle;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.UserCounselDetailFragment;
import com.xinheng.mvp.model.user.UserCounselItem;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：我的咨询详情
 */
public class UserCounselDetailActivity extends UserBaseActivity
{
    public static void actionUserCounselDetail(BaseActivity activity,UserCounselItem item)
    {
        Intent intent = new Intent(activity, UserCounselDetailActivity.class);
        intent.putExtra(EXTRA_ITEM ,item);
        activity.startActivity(intent);
    }
    private UserCounselItem mUserCounselItem;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mUserCounselItem = getIntent().getSerializableExtra(EXTRA_ITEM)==null?null: (UserCounselItem) getIntent().getSerializableExtra(EXTRA_ITEM) ;
        setContentView(R.layout.activity_activity_common);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, UserCounselDetailFragment.newInstance(mUserCounselItem)).commit();
    }

  
    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_user_counsel_detail);
    }
}
