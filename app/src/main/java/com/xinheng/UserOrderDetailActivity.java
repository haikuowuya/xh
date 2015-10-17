package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.UserOrderDetailFragment;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：我的订单详情页面
 */
public class UserOrderDetailActivity extends UserBaseActivity
{
    public static final String  EXTRA_ID="id";
    public static void actionUserOrderDetail(BaseActivity activity,String reportId)
    {
        Intent intent = new Intent(activity, UserOrderDetailActivity.class);
        intent.putExtra(EXTRA_ID, reportId);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        String reportId = getIntent().getStringExtra(EXTRA_ID);
        if(!TextUtils.isEmpty(reportId))
        {
            setContentView(R.layout.activity_activity_common);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, UserOrderDetailFragment.newInstance(reportId)).commit();
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
        return getString(R.string.tv_activity_user_order_detail);
    }
}
