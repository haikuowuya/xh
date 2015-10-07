package com.xinheng.adapter.auto;

import android.view.View;

import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.mvp.model.auto.BodyKV;

import java.util.List;

/**
 * 身体部位popupwindow列表适配器
 */
public class BodyPartListAdapter extends BaseAdapter<BodyKV>
{
    public BodyPartListAdapter(BaseActivity activity, List<BodyKV> data)
    {
        super(activity, R.layout.list_auto_popup_list_item, data);
    }

    @Override
    public void bindDataToView(View convertView, BodyKV kv)
    {
        setTextViewText(convertView, R.id.tv_text, kv.value);
    }
}
