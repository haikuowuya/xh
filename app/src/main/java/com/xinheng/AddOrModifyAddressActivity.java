package com.xinheng;

import android.content.Intent;
import android.os.Bundle;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.AddOrModifyAddressFragment;
import com.xinheng.mvp.model.address.AddressItem;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明:新增收货地址
 */
public class AddOrModifyAddressActivity extends BaseActivity
{
    private AddressItem mAddressItem;
    public static void actionAddAddress(BaseActivity activity)
    {
         actionAddAddress(activity, null);
    }
    public static void actionAddAddress(BaseActivity activity,AddressItem addressItem)
    {
        Intent intent = new Intent(activity, AddOrModifyAddressActivity.class);
        intent.putExtra(EXTRA_ITEM, addressItem);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mAddressItem = getIntent().getSerializableExtra(EXTRA_ITEM)==null?null: (AddressItem) getIntent().getSerializableExtra(EXTRA_ITEM);
        setContentView(R.layout.activity_activity_common);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, AddOrModifyAddressFragment.newInstance(mAddressItem)).commit();

    }



    @Override
    public CharSequence getActivityTitle()
    {
        String title =    getString(R.string.tv_activity_add_address);
        if(null != mAddressItem)
        {
            title="修改收货地址";
        }
        return   title;
    }
}
