package com.xinheng.adapter.user;

import android.app.Activity;
import android.view.View;

import com.xinheng.R;
import com.xinheng.base.BaseAdapter;
import com.xinheng.mvp.model.IconTextItem;

import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/19 0019
 * 时间： 13:22
 * 说明：
 */
public class CustomAdapter extends BaseAdapter<IconTextItem>
{
    public CustomAdapter(Activity activity, List<IconTextItem> data)
    {
        super(activity, R.layout.list_icon_text_item, data);
    }

    @Override
    public void bindDataToView(View convertView, IconTextItem iconTextItem)
    {
        if(null != iconTextItem)
        {
            setImageViewResId(convertView, R.id.iv_icon, iconTextItem.iconId);
            setTextViewText(convertView, R.id.tv_text, iconTextItem.text);
        }
    }
}
