package com.xinheng.adapter.depart;

import android.view.View;

import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.mvp.model.DepartItem;

import java.util.List;

/**
 * Created by Steven on 2015/9/9 0009.
 */
public class DepartLeftListAdapter extends BaseAdapter<DepartItem>
{

    public DepartLeftListAdapter(BaseActivity activity, List<DepartItem> data)
    {
        super(activity, R.layout.list_department_left_item, data);
    }

    @Override
    public void bindDataToView(View convertView, DepartItem departItem)
    {
         setTextViewText(convertView, R.id.tv_text, departItem.name);
    }
}
