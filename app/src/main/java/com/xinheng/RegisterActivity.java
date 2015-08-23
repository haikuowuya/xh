package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xinheng.base.BaseActivity;
import com.xinheng.slidingmenu.SlidingMenu;

/**
 *  用户注册界面
 */
public class RegisterActivity extends BaseActivity
    
{
    public static void actionRegister(BaseActivity activity)
    {
        Intent intent = new Intent(activity, RegisterActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);//TODO
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        configTitleLayout();
    }

    private void configTitleLayout()
    {
        getTitleContainer().setVisibility(View.GONE);
        ((View)getContentViewGroup().getParent()).setPadding(0,0,0,0);
    }

    @Override
    public CharSequence getActivityTitle()
    {
        
        return getString(R.string.tv_activity_register);
    }
    
    
}
