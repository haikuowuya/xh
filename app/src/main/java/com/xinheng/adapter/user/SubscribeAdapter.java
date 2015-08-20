package com.xinheng.adapter.user;

import android.app.Activity;
import android.view.View;

import com.xinheng.R;
import com.xinheng.base.BaseAdapter;
import com.xinheng.mvp.model.UserSubscribeItem;

import java.util.List;

/**
 * 我的预约列表适配器
 *
 */
public class SubscribeAdapter extends BaseAdapter<UserSubscribeItem>
{
    public SubscribeAdapter(Activity activity, List<UserSubscribeItem> data)
    {
        super(activity, R.layout.list_user_subscribe_item, data);
    }

    @Override
    public void bindDataToView(View convertView, UserSubscribeItem userSubscribeItem)
    {
        setTextViewText(convertView, R.id.tv_sub_time,"预约时间："+ userSubscribeItem.createTime);
        setTextViewText(convertView, R.id.tv_appo_time, "申请时间："+userSubscribeItem.appointmentTime);
        setTextViewText(convertView, R.id.tv_content, userSubscribeItem.content);
        setTextViewText(convertView, R.id.tv_dept_name, userSubscribeItem.department+"/"+userSubscribeItem.doctorName);
        setTextViewText(convertView, R.id.tv_hospital_name,"就诊机构："+ userSubscribeItem.hospital);
    }
}
