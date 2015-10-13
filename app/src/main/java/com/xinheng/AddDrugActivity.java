package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.AddDrugFragment;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：按方抓药-添加药品界面
 */
public class AddDrugActivity extends BaseActivity
{
    private  static  final  String EXTRA_DRUG_ITEMS_JSON="drug_items_json";
    public static void actionAddDrug(BaseActivity activity,String drugItemsJson)
    {
        Intent intent = new Intent(activity, AddDrugActivity.class);
        intent.putExtra(EXTRA_DRUG_ITEMS_JSON, drugItemsJson);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_common);
        String drugItemsJson = getIntent().getStringExtra(EXTRA_DRUG_ITEMS_JSON);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, AddDrugFragment.newInstance(drugItemsJson)).commit();
          configTitle();
    }

    private void configTitle()
    {
        TextView tvRightTitle = getRightTitleView();
        if (null != tvRightTitle)
        {
            tvRightTitle.setVisibility(View.VISIBLE);
            setIvRightVisibility(View.GONE);
            tvRightTitle.setText("完成");
            tvRightTitle.setOnClickListener(
                    new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            mActivity.finish();
                        }
                    });
        }

    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_add_drug);
    }
}
