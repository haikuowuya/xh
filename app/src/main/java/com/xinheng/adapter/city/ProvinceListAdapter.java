package com.xinheng.adapter.city;

import android.view.View;

import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.mvp.model.city.CityItem;

import java.util.List;

/**
 * 省份选择列表适配器
 */
public class ProvinceListAdapter extends BaseAdapter<CityItem>
{
    public ProvinceListAdapter(BaseActivity activity, List<CityItem> data)
    {
        super(activity, R.layout.list_city_item, data);
    }

    @Override
    public void bindDataToView(View convertView, CityItem cityItem)
    {
        setTextViewText(convertView, R.id.tv_text, cityItem.name);
    }
}
