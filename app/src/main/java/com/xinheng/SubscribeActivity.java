package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.SubscribeFragment;
import com.xinheng.mvp.model.doctor.DoctorScheduleItem;
import com.xinheng.util.GsonUtils;

import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：预约挂号界面
 */
public class SubscribeActivity extends BaseActivity
{
    public static final String EXTRA_JSON_SCHEDULE_ITEMS = "json_schedule_item";

    public static void actionSubscribe(BaseActivity activity, List<DoctorScheduleItem> items)
    {
        Intent intent = new Intent(activity, SubscribeActivity.class);
        intent.putExtra(EXTRA_JSON_SCHEDULE_ITEMS, GsonUtils.toJson(items));
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //医生排班列表信息
        String scheduleItemsJson = getIntent().getStringExtra(EXTRA_JSON_SCHEDULE_ITEMS);
        if (!TextUtils.isEmpty(scheduleItemsJson))
        {
            setContentView(R.layout.activity_activity_common);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, SubscribeFragment.newInstance(scheduleItemsJson)).commit();
        }
        else
        {
            mActivity.showCroutonToast("医生排班信息为空， 非法操作");
            mActivity.finish();
        }
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_subscribe);
    }
}
