package com.xinheng;

import android.content.Intent;
import android.os.Bundle;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.AddRecipeFragment;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：添加处方界面
 */
public class AddRecipeActivity extends BaseActivity
{
    public static void actionAddRecipe(BaseActivity activity)
    {
        Intent intent = new Intent(activity, AddRecipeActivity.class);
        activity.startActivity(intent);
    }

    private AddRecipeFragment mAddRecipeFragment;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_common);
        mAddRecipeFragment = AddRecipeFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, mAddRecipeFragment).commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        mAddRecipeFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_add_recipe);
    }
}
