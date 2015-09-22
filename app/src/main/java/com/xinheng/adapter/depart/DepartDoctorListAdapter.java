package com.xinheng.adapter.depart;

import android.view.View;

import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.mvp.model.depart.DepartDoctorItem;

import java.util.List;

/**
 * 科室医生列表适配器
 */
public class DepartDoctorListAdapter   extends BaseAdapter<DepartDoctorItem>
{
    public DepartDoctorListAdapter(BaseActivity activity,  List<DepartDoctorItem> data)
    {
        super(activity, R.layout.list_depart_doctor_item, data);
    }

    @Override
    public void bindDataToView(View convertView, DepartDoctorItem departDoctorItem)
    {
        setTextViewText(convertView, R.id.tv_skill, departDoctorItem.skill);
        setTextViewText(convertView, R.id.tv_doctor_name, departDoctorItem.doctorName);
        setTextViewText(convertView, R.id.tv_technical_post, departDoctorItem.technicalPost);
        setTextViewText(convertView, R.id.tv_dept_name, departDoctorItem.department);
    }
}
