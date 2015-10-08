package com.xinheng.adapter.user;

import android.text.TextUtils;
import android.view.View;

import com.xinheng.R;
import com.xinheng.UserAppointmentAddDetailActivity;
import com.xinheng.UserAppointmentDetailActivity;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.fragment.UserAppointmentListFragment;
import com.xinheng.mvp.model.user.UserAppointmentItem;
import com.xinheng.util.DateFormatUtils;

import java.util.List;

/**
 * 我的预约列表适配器
 */
public class UserAppointmentListAdapter extends BaseAdapter<UserAppointmentItem>
{
    private String mType;
    public UserAppointmentListAdapter(BaseActivity activity, List<UserAppointmentItem> data,String type)
    {
        super(activity, R.layout.list_user_subscribe_item, data);
        mType = type;
    }

    @Override
    public void bindDataToView(View convertView, final UserAppointmentItem userAppointmentItem)
    {
        String subscribeTime = DateFormatUtils.format(userAppointmentItem.createTime, true);
        setTextViewText(convertView, R.id.tv_subscribe_time, subscribeTime);
        String appointmentTime = DateFormatUtils.format(userAppointmentItem.appointmentTime, true);
        setTextViewText(convertView, R.id.tv_subscribe_appointment_time, appointmentTime);
        String content = " 暂无留言";
        if (!TextUtils.isEmpty(userAppointmentItem.content))
        {
            content = userAppointmentItem.content;
        }
        setTextViewText(convertView, R.id.tv_subscribe_msg, content);
        setTextViewText(convertView, R.id.tv_subscribe_dept, userAppointmentItem.department + "/" + userAppointmentItem.doctName);
        setTextViewText(convertView, R.id.tv_subscribe_hospital, userAppointmentItem.hospital);

        setViewOnClick(
                convertView, R.id.tv_detail, new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
//                getActivity().showCroutonToast("查看详情");

                        if (UserAppointmentListFragment.TYPE_0.equals(mType))
                        {
                            UserAppointmentDetailActivity.actionUserAppointmentDetail(getActivity(), userAppointmentItem.id);
                        }
                        else if (UserAppointmentListFragment.TYPE_1.equals(mType))
                        {
                            UserAppointmentAddDetailActivity.actionUserAppointmentAddDetail(getActivity(), userAppointmentItem.id);
                        }
                    }
                });

    }
}
