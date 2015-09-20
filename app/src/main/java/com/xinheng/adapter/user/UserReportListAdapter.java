package com.xinheng.adapter.user;

import android.view.View;

import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.mvp.model.user.UserReportItem;

import java.util.List;

/**
 * 我的报告列表适配器
 */
public class UserReportListAdapter extends BaseAdapter<UserReportItem>
{
    public UserReportListAdapter(BaseActivity activity, List<UserReportItem> data)
    {
        super(activity, R.layout.list_user_medical_item, data);
    }

    @Override
    public void bindDataToView(View convertView, UserReportItem item)
    {
         setTextViewText(convertView, R.id.tv_hospital_dept, item.name);
         setTextViewText(convertView, R.id.tv_text, item.name);
    }
}
