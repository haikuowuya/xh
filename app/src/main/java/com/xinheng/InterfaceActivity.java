package com.xinheng;

import android.content.Intent;
import android.os.Bundle;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.InterfaceFragment;

import java.io.Serializable;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：接口测试Activity
 */
public class InterfaceActivity extends BaseActivity
{

    public static  final String EXTRA_LOGIN_SUCCESS_ITEM ="login_success_item";

    public static void actionInterface(BaseActivity activity, Serializable loginSuccessItem)
    {
        Intent intent = new Intent(activity, InterfaceActivity.class);
        intent.putExtra(EXTRA_LOGIN_SUCCESS_ITEM, loginSuccessItem);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_common);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, InterfaceFragment.newInstance( getIntent().getSerializableExtra(EXTRA_LOGIN_SUCCESS_ITEM))).commit();
    }

  
    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_interface);
    }
}
