package com.xinheng;

import android.content.Intent;
import android.os.Bundle;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.SearchDrugResultFragment;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：点击在线售药最底部的药品时，搜索结果药品Activity页面
 */
public class SearchDrugResultActivity extends BaseActivity
{
    private static final String EXTRA_SEARCH_KEY = "search_key";

    public static void actionSearchDrugResult(BaseActivity activity, String searchKey)
    {
        Intent intent = new Intent(activity, SearchDrugResultActivity.class);
        intent.putExtra(EXTRA_SEARCH_KEY, searchKey);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_common);
        String searchKey = getIntent().getStringExtra(EXTRA_SEARCH_KEY);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, SearchDrugResultFragment.newInstance(searchKey)).commit();

    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_search_drug_result);
    }
}
