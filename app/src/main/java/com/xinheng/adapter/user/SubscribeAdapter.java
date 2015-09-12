package com.xinheng.adapter.user;

import android.text.TextUtils;
import android.view.View;

import com.xinheng.R;
import com.xinheng.UserSubscribeDetailActivity;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.mvp.model.UserSubscribeItem;
import com.xinheng.util.DateFormatUtils;

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
        String subscribeTime = DateFormatUtils.format(userSubscribeItem.createTime,true);
        setTextViewText(convertView, R.id.tv_subscribe_time,   subscribeTime);
        String appointmentTime = DateFormatUtils.format(userSubscribeItem.appointmentTime,true);
        setTextViewText(convertView, R.id.tv_subscribe_appointment_time,  appointmentTime);
        String content ="暂无留言";
        if(!TextUtils.isEmpty(userSubscribeItem.content))
        {
            content = userSubscribeItem.content;
        }
        setTextViewText(convertView, R.id.tv_subscribe_msg,  content);
        setTextViewText(convertView, R.id.tv_subscribe_dept, userSubscribeItem.department+"/"+userSubscribeItem.doctName);
        setTextViewText(convertView, R.id.tv_subscribe_hospital, userSubscribeItem.hospital);

        setViewOnClick(convertView, R.id.tv_detail, new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                getActivity().showCroutonToast("查看详情");
                UserSubscribeDetailActivity.actionUserSubscribeDetail(getActivity());
            }
        });

    }
}
