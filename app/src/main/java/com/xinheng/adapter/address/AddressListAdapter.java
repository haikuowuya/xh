package com.xinheng.adapter.address;

import android.view.View;

import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.mvp.model.address.AddressItem;

import java.util.List;

/**
 * Created by Administrator on 2015/9/20 0020.
 */
public class AddressListAdapter extends BaseAdapter<AddressItem>
{
    public AddressListAdapter(BaseActivity activity , List<AddressItem> data)
    {
        super(activity, R.layout.list_address_item, data);
    }

    @Override
    public void bindDataToView(View convertView, AddressItem addressItem)
    {
         setTextViewText(convertView, R.id.tv_name, addressItem.name);
        String address = addressItem.city+ addressItem.address;
        setTextViewText(convertView, R.id.tv_address, address);
        setTextViewText(convertView, R.id.tv_phone,addressItem.mobile);
    }
}
