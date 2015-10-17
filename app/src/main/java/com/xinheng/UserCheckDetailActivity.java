package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.UserCheckDetailFragment;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：我的检查详情页面
 */
public class UserCheckDetailActivity extends UserBaseActivity
{
    public static final String  EXTRA_ID="id";
    public static void actionUserCheckDetail(BaseActivity activity,String chedkId)
    {
        Intent intent = new Intent(activity, UserCheckDetailActivity.class);
        intent.putExtra(EXTRA_ID, chedkId);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        String chedkId = getIntent().getStringExtra(EXTRA_ID);
        if(!TextUtils.isEmpty(chedkId))
        {
            setContentView(R.layout.activity_activity_common);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, UserCheckDetailFragment.newInstance(chedkId)).commit();
        }
        else
        {
            showToast("处方id为空，非法操作");
            finish();
        }

    }



    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_user_check_detail);
    }
}
