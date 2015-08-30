package com.xinheng.adapter.user;

import android.view.View;

import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.mvp.model.UserSubscribeItem;

import java.util.List;

/**
 * 我的预约列表适配器
 *
 */
public class SubscribeAdapter extends BaseAdapter<UserSubscribeItem>
{
    public SubscribeAdapter(BaseActivity activity, List<UserSubscribeItem> data)
    {
        super(activity, R.layout.list_user_subscribe_item, data);
    }

    @Override
    public void bindDataToView(View convertView, UserSubscribeItem userSubscribeItem)
    {
        setTextViewText(convertView, R.id.tv_subscribe_time,"预约时间："+ userSubscribeItem.createTime);
        setTextViewText(convertView, R.id.tv_subscribe_appointment_time, "申请时间："+userSubscribeItem.appointmentTime);
        setTextViewText(convertView, R.id.tv_subscribe_msg, userSubscribeItem.content);
        setTextViewText(convertView, R.id.tv_subscribe_dept, userSubscribeItem.department+"/"+userSubscribeItem.doctorName);
        setTextViewText(convertView, R.id.tv_subscribe_hospital,"就诊机构："+ userSubscribeItem.hospital);

        setViewOnClick(convertView, R.id.tv_detail, new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getActivity().showCroutonToast("查看详情");
            }
        });

    }
}
