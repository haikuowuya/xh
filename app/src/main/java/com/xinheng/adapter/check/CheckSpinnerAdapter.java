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
public class CheckSpinnerAdapter extends BaseAdapter<DepartCheckItem>
{
    public CheckSpinnerAdapter(BaseActivity activity, List<DepartCheckItem> data)
    {
        super(activity, R.layout.list_check_spinner_item, data);
    }

    @Override
    public void bindDataToView(View convertView, DepartCheckItem departCheckItem)
    {
        setTextViewText(convertView, R.id.tv_text, departCheckItem.deptName);
    }
}
