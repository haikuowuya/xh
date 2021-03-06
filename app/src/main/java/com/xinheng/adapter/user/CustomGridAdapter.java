package com.xinheng.adapter.user;

import android.view.View;

import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.mvp.model.IconTextItem;

import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/19 0019
 * 时间： 13:22
 * 说明：个人中心 grid 适配器(我的订单、我的检测……)
 */
public class CustomGridAdapter extends BaseAdapter<IconTextItem>
{
    public CustomGridAdapter(BaseActivity activity, List<IconTextItem> data)
    {
        super(activity, R.layout.grid_user_icon_text_item, data);
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
