package com.xinheng.adapter.city;

import android.view.View;

import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;

import java.util.List;

/**
 *地区选择列表适配器
 */
public class AreaListAdapter extends BaseAdapter<String>
{
    public AreaListAdapter(BaseActivity activity, List<String> data)
    {
        super(activity, R.layout.list_city_item, data);
    }

    @Override
    public void bindDataToView(View convertView, String text)
    {
        setTextViewText(convertView, R.id.tv_text, text);
    }
}
