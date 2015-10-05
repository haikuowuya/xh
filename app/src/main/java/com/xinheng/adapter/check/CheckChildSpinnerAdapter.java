package com.xinheng.adapter.check;

import android.view.View;

import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.mvp.model.check.DepartCheckItem;

import java.util.List;

/**
 * Created by Administrator on 2015/10/4 0004.
 */
public class CheckChildSpinnerAdapter extends BaseAdapter<DepartCheckItem.CheckItem>
{
    public CheckChildSpinnerAdapter(BaseActivity activity, List<DepartCheckItem.CheckItem> data)
    {
        super(activity, R.layout.list_check_spinner_item, data);
    }

    @Override
    public void bindDataToView(View convertView, DepartCheckItem.CheckItem checkItem)
    {
        setTextViewText(convertView, R.id.tv_text, checkItem.name);
    }
}
