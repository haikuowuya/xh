package com.xinheng.eventbus;

import com.xinheng.mvp.model.address.AddressItem;

/**
 * 选择地址事件
 */
public class OnSelectAddressEvent extends  BaseOnEvent
{
    public AddressItem mAddressItem;

    public OnSelectAddressEvent(AddressItem addressItem)
    {
        mAddressItem = addressItem;
    }
}
