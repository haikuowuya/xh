package com.xinheng.adapter.user;

import android.app.Activity;
import android.view.View;

import com.xinheng.R;
import com.xinheng.base.BaseAdapter;
import com.xinheng.mvp.model.UserDoctorItem;

import java.util.List;

/**
 * 我的医生列表适配器
 */
public class UserDoctorListAdapter extends BaseAdapter<UserDoctorItem>
{
    public UserDoctorListAdapter(Activity activity, List<UserDoctorItem> data)
    {
        super(activity, R.layout.list_user_doctor_item, data);
    }

    @Override
    public void bindDataToView(View convertView, UserDoctorItem item)
    {
        setTextViewText(convertView, R.id.tv_time, "上次就诊时间：" + item.lastServiceTime);
        setTextViewText(convertView, R.id.tv_doctor_name, item.doctName);
        setTextViewText(convertView, R.id.tv_dept_name, item.hospital + "/" + item.department);
        setTextViewText(convertView, R.id.tv_hospital_name, item.hospital);
    }
}
