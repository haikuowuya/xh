package com.xinheng;

import android.content.Intent;
import android.os.Bundle;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.DrugDetailFragment;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明： 药品详情
 */
public class DrugDetailActivity extends BaseActivity
{
    private  static  final  String EXTRA_DRUG_Id="drug_id";
    public static void actionDrugDetail(BaseActivity activity,String drugId)
    {
        Intent intent = new Intent(activity, DrugDetailActivity.class);
        intent.putExtra(EXTRA_DRUG_Id, drugId);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_common);
        String drugId = getIntent().getStringExtra(EXTRA_DRUG_Id);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, DrugDetailFragment.newInstance(drugId)).commit();
    }
    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_drug_detail);
    }
}
