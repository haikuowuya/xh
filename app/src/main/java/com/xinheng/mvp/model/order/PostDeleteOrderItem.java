package com.xinheng.mvp.model.order;

import com.xinheng.mvp.model.PostItem;

/**
 *  删除我的订单列表时，向服务器端传送的POST实体item
 */
public  class PostDeleteOrderItem extends PostItem
{
    /**
     * 订单ID
     */
    public String orderId;
}