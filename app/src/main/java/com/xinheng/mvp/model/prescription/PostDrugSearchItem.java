package com.xinheng.mvp.model.prescription;

import com.xinheng.mvp.model.order.PostOrderItem;

/**
 *  搜索药品时，向服务器端传送的POST实体item
 */
public class PostDrugSearchItem extends PostOrderItem
{
    public String keyword;
    /***
     * 医院id
     */
    public String hid;


}
