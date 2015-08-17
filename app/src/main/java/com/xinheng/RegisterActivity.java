package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xinheng.base.BaseActivity;

/**
 * Created by Administrator on 2015/8/17 0017.
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
        configTitleLayout();
        
    }

   

    private void configTitleLayout()
    {
        getTitleContainer().setVisibility(View.GONE);
    }

    @Override
    public CharSequence getActivityTitle()
    {
        
        return getString(R.string.tv_activity_register);
    }
    
    
}
