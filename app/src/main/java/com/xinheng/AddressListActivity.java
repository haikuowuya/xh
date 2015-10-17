package com.xinheng;

import android.content.Intent;
import android.os.Bundle;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.AddressListFragment;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明:收货地址管理列表
 */
public class AddressListActivity extends UserBaseActivity
{
    public static final String EXTRA_FROM_CONFIRM_ORDER = "from_confirm_order";

    public static void actionAddressManager(BaseActivity activity)
    {
         actionAddressManager(activity,false);
    }

    public static void actionAddressManager(BaseActivity activity, boolean fromConfirmOrder)
    {
        Intent intent = new Intent(activity, AddressListActivity.class);
        intent.putExtra(EXTRA_FROM_CONFIRM_ORDER, fromConfirmOrder);
        activity.startActivity(intent);
    }

    private boolean mFromConfirmOrder = false;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_common);
        mFromConfirmOrder = getIntent().getBooleanExtra(EXTRA_FROM_CONFIRM_ORDER, false);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, AddressListFragment.newInstance(mFromConfirmOrder)).commit();

    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_address_manager);
    }
}
