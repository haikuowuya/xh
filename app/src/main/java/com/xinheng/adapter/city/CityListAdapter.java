package com.xinheng.adapter.city;

import android.view.View;

import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.mvp.model.city.CityItem;

import java.util.List;

/**
 *  城市选择列表适配器
 */
public class CityListAdapter extends BaseAdapter<CityItem.AreaItem>
{
    public CityListAdapter(BaseActivity activity, List<CityItem.AreaItem> data)
    {
        super(activity, R.layout.list_city_item, data);
    }

    @Override
    public void bindDataToView(View convertView, CityItem.AreaItem areaItem)
    {
        setTextViewText(convertView, R.id.tv_text, areaItem.name);
    }
}
