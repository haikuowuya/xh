package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.UserRecipeDetailFragment;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：我的处方详情页面
 */
public class UserRecipeDetailActivity extends BaseActivity
{
    public static final String  EXTRA_ID="id";
    public static void actionUserRecipeDetail(BaseActivity activity,String recipeId)
    {
        Intent intent = new Intent(activity, UserRecipeDetailActivity.class);
        intent.putExtra(EXTRA_ID, recipeId);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        String recipeId = getIntent().getStringExtra(EXTRA_ID);
        if(!TextUtils.isEmpty(recipeId))
        {
            setContentView(R.layout.activity_activity_common);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, UserRecipeDetailFragment.newInstance(recipeId)).commit();
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
        return getString(R.string.tv_activity_user_recipe_detail);
    }
}
