package com.xinheng.eventbus;

import com.xinheng.mvp.model.prescription.PostPayDespatchItem;

/**
 * Created by Steven on 2015/9/22 0022.
 */
public class OnSelectPayDespatchEvent extends  BaseOnEvent
{
    public PostPayDespatchItem mPostPayDespatchItem;

    public OnSelectPayDespatchEvent(PostPayDespatchItem postPayDespatchItem)
    {
        mPostPayDespatchItem = postPayDespatchItem;
    }
}
