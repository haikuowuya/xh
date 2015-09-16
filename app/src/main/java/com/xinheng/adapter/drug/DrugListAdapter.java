package com.xinheng.adapter.drug;

import android.view.View;
import android.widget.TextView;

import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.base.ViewHolder;
import com.xinheng.mvp.model.prescription.DrugItem;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/15 0015.
 */
public class DrugListAdapter extends BaseAdapter<DrugItem>
{
    private LinkedList<DrugItem> mSelectedItems = new LinkedList<>();
    public DrugListAdapter(BaseActivity activity  , List<DrugItem> data)
    {
        super(activity, R.layout.list_drug_item, data);
    }

    @Override
    public void bindDataToView(View convertView, final DrugItem drugItem)
    {
      setTextViewText(convertView, R.id.tv_drug_name, drugItem.name);
        setTextViewText(convertView, R.id.tv_drug_nature, drugItem.nature);

        final TextView tvAdd = ViewHolder.getView(convertView , R.id.tv_add);
        if(mSelectedItems.contains(drugItem))
        {
           tvAdd.setText("取消");
        }
        else
        {
            tvAdd.setText("加入处方");
        }
        tvAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if("取消".equals(tvAdd.getText().toString()))
                {
                    mSelectedItems.remove(drugItem);
                    tvAdd.setText("加入处方");
                }
                else
                {      tvAdd.setText("取消");
                    mSelectedItems.add(drugItem);
                }
            }
        });
    }
}
