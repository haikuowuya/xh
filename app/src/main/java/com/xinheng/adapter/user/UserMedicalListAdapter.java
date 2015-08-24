package com.xinheng.adapter.user;

import android.app.Activity;
import android.view.View;

import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.mvp.model.UserMedicalItem;

import java.util.List;

/**
 * 我的医生列表适配器
 */
public class UserMedicalListAdapter extends BaseAdapter<UserMedicalItem>
{
    public UserMedicalListAdapter(BaseActivity activity, List<UserMedicalItem> data)
    {
        super(activity, R.layout.list_user_medical_item, data);
    }

    @Override
    public void bindDataToView(View convertView, UserMedicalItem item)
    {
         setTextViewText(convertView, R.id.tv_hospital_dept, item.name);
         setTextViewText(convertView, R.id.tv_text, item.name);
    }
}
