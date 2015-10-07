package com.xinheng.adapter.auto;

import android.view.View;

import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.mvp.model.auto.BodyKV;

import java.util.List;

/**
 * 身体症状popupwindow列表适配器
 */
public class SymptomListAdapter extends BaseAdapter<BodyKV.SymptomItem>
{
    public SymptomListAdapter(BaseActivity activity, List<BodyKV.SymptomItem> data)
    {
        super(activity, R.layout.list_auto_popup_list_item, data);
    }

    @Override
    public void bindDataToView(View convertView, BodyKV.SymptomItem kv)
    {
        setTextViewText(convertView, R.id.tv_text, kv.name);
    }
}
