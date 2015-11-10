package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.ShoppingCartConfirmOrderFragment;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：确认订单页面
 */
public class ShoppingCartConfirmOrderActivity extends BaseActivity
{
    public static final String EXTRA_DRUG_JSON="drug_json";
    public static final String EXTRA_FEE="fee";
    public static void actionShoppingCartConfirmOrder(BaseActivity activity,String drugJson ,String fee)
    {
        Intent intent = new Intent(activity, ShoppingCartConfirmOrderActivity.class);
        intent.putExtra(EXTRA_DRUG_JSON, drugJson);
        intent.putExtra(EXTRA_FEE, fee);
        activity.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        String drugJson  = getIntent().getStringExtra(EXTRA_DRUG_JSON);
        String fee = getIntent().getStringExtra(EXTRA_FEE);
        if (!TextUtils.isEmpty(drugJson))
        {
            setContentView(R.layout.activity_activity_common);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, ShoppingCartConfirmOrderFragment.newInstance(drugJson,fee)).commit();
        }
        else
        {
            mActivity.showCroutonToast("非法操作， ");
            finish();
        }
    }

  
    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_confirm_order);
    }
}
