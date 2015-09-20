package com.xinheng.eventbus;

import com.xinheng.mvp.model.user.UserOrderItem;

/**
 * 删除订单事件
 */
public class OnDeleteUserOrderEvent extends  BaseOnEvent
{
    public UserOrderItem mUserOrderItem;

    public OnDeleteUserOrderEvent(UserOrderItem userOrderItem)
    {
        mUserOrderItem = userOrderItem;
    }
}
