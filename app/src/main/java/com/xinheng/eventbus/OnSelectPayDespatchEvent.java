package com.xinheng.eventbus;

import com.xinheng.mvp.model.prescription.PostPayDespatchItem;

/**
 *  选择支付方式事件
 */
public class OnSelectPayDespatchEvent extends  BaseOnEvent
{
    public PostPayDespatchItem mPostPayDespatchItem;

    public OnSelectPayDespatchEvent(PostPayDespatchItem postPayDespatchItem)
    {
        mPostPayDespatchItem = postPayDespatchItem;
    }
}
