package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.ConfirmOrderFragment;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：确认订单页面
 */
public class ConfirmOrderActivity extends BaseActivity
{
    public static final String EXTRA_ORDER_ID="order_id";
    public static void actionConfirmOrder(BaseActivity activity,String orderId )
    {
        Intent intent = new Intent(activity, ConfirmOrderActivity.class);
        intent.putExtra(EXTRA_ORDER_ID, orderId);
        activity.startActivity(intent);
    }

    private String mOrderId;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mOrderId = getIntent().getStringExtra(EXTRA_ORDER_ID);
        if (!TextUtils.isEmpty(mOrderId))
        {
            setContentView(R.layout.activity_activity_common);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, ConfirmOrderFragment.newInstance(mOrderId)).commit();
        }
        else
        {
            mActivity.showCroutonToast("非法操作，订单id为空");
            finish();
        }
    }

  
    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_confirm_order);
    }
}
