package com.xinheng.adapter.depart;

import android.view.View;

import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.mvp.model.DepartItem;

import java.util.List;

/**
 *科室列表右侧适配器
 */
public class DepartRightListAdapter extends BaseAdapter<DepartItem>
{

    public DepartRightListAdapter(BaseActivity activity, List<DepartItem> data)
    {
        super(activity, R.layout.list_department_right_item, data);
    }

    @Override
    public void bindDataToView(View convertView, DepartItem departItem)
    {
         setTextViewText(convertView, R.id.tv_text, departItem.name);
    }
}
