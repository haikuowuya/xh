package com.xinheng.eventbus;

import com.xinheng.mvp.model.address.AddressItem;

/**
 * Created by Steven on 2015/9/22 0022.
 */
public class OnSelectAddressEvent extends  BaseOnEvent
{
    public AddressItem mAddressItem;

    public OnSelectAddressEvent(AddressItem addressItem)
    {
        mAddressItem = addressItem;
    }
}
