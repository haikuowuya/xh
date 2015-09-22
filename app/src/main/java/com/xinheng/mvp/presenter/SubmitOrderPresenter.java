package com.xinheng.mvp.presenter;

import com.xinheng.mvp.model.prescription.PostPayDespatchItem;

/**
 *  提交订单接口
 */
public interface SubmitOrderPresenter
{
    public  void  doSubmitOrder(PostPayDespatchItem item);
}
