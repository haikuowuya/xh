package com.xinheng.adapter.main;

import android.app.Activity;
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
 * 说明：顶部广告下面的分类模块
 */
public class GridAdapter extends BaseAdapter<HomeGridItem>
{
    public GridAdapter(BaseActivity activity, List<HomeGridItem> data)
    {
        super(activity, R.layout.grid_home_item, data);
    }

    @Override
    public void bindDataToView(View convertView, HomeGridItem homeGridItem)
    {
        setImageViewResId(convertView, R.id.iv_image, homeGridItem.resId);
        setTextViewText(convertView, R.id.tv_text, homeGridItem.text);
    }
}
