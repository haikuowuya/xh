package com.xinheng.adapter;

import android.view.View;
import android.widget.TextView;

import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.base.ViewHolder;
import com.xinheng.mvp.model.IconTextItem;
import com.xinheng.util.DensityUtils;

import java.util.List;

/**
 * Created by Administrator on 2015/9/21 0021.
 */
public class PopupListAdapter extends BaseAdapter<IconTextItem>
{
    public PopupListAdapter(BaseActivity activity, List<IconTextItem> data)
    {
        super(activity, R.layout.list_popupwindow_item, data);
    }

    @Override
    public void bindDataToView(View convertView, IconTextItem iconTextItem)
    {
        TextView textView = ViewHolder.getView(convertView, R.id.tv_text);
        textView.setText(iconTextItem.text);
        textView.setCompoundDrawablePadding(DensityUtils.dpToPx(getActivity(), 8.f));
        textView.setCompoundDrawablesWithIntrinsicBounds(iconTextItem.iconId, 0,0,0);
    }
}
