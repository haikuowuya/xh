package com.xinheng.adapter.address;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.mvp.model.address.AddressItem;

import java.util.List;

/**
 * 收货地址列表适配器
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
        String   defaultHint ="[默认]";
        if("1".equals(addressItem.isDefault))
        {
            address = defaultHint+address;
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(address);
            spannableStringBuilder.setSpan(new ForegroundColorSpan(0xFFFF2608),0,defaultHint.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            setTextViewText(convertView, R.id.tv_address, spannableStringBuilder);
        }
        else
        {
            setTextViewText(convertView, R.id.tv_address, address);
        }
        setTextViewText(convertView, R.id.tv_phone,addressItem.mobile);
    }
}
