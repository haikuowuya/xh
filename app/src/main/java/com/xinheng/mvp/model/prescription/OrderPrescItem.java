package com.xinheng.mvp.model.prescription;

import com.xinheng.mvp.model.BaseEmptyItem;

/**
 *订单id，药方id 实体类
 */
public class OrderPrescItem  extends BaseEmptyItem
{
   public String  orderId;//订单id
    public String prescId;//药方id

    @Override
    public String toString()
    {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("订单id = "+orderId)  ;
        stringBuffer.append(" 药方id = "+prescId)  ;

        return  stringBuffer.toString();
    }
}
