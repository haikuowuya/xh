package com.xinheng.adapter.online;

import android.view.View;

import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.mvp.model.HomeGridItem;

import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/17 0017
 * 时间： 18:39
 * 说明：在线售药最下面的grid
 */
public class OnlineBottomGridAdapter extends BaseAdapter<HomeGridItem>
{
    public OnlineBottomGridAdapter(BaseActivity activity, List<HomeGridItem> data)
    {
        super(activity, R.layout.grid_online_bottom_item, data);
    }

    @Override
    public void bindDataToView(View convertView, HomeGridItem homeGridItem)
    {
        setImageViewResId(convertView, R.id.iv_image, homeGridItem.resId);
        setTextViewText(convertView, R.id.tv_text, homeGridItem.text);
    }
}
