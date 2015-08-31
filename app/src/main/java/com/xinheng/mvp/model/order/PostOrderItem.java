package com.xinheng.mvp.model.order;

import com.xinheng.mvp.model.PostItem;

/**
 *  获取我的订单列表时，向服务器端传送的POST实体item
 */
public  class PostOrderItem extends PostItem
{
    /**
     * 订单状态
     */
    public String state;
}