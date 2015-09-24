package com.xinheng.adapter.doctor;

import android.view.View;

import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.mvp.model.doctor.DoctorScheduleItem;

import java.util.List;

/**
 * Created by Steven on 2015/9/24 0024.
 */
public class ScheduleListAdapter extends BaseAdapter<DoctorScheduleItem>
{
    public ScheduleListAdapter(BaseActivity activity, List<DoctorScheduleItem> data)
    {
        super(activity, R.layout.list_doctor_schedule_item, data);
    }

    @Override
    public void bindDataToView(View convertView, DoctorScheduleItem doctorScheduleItem)
    {
        setTextViewText(convertView, R.id.tv_date, doctorScheduleItem.date);
        setTextViewText(convertView, R.id.tv_time, doctorScheduleItem.begintime + "-" + doctorScheduleItem.endtime);
        setTextViewText(convertView, R.id.tv_type, doctorScheduleItem.type);

    }
}
