package com.xinheng.eventbus;

import com.xinheng.mvp.model.prescription.DrugItem;

import java.util.List;

/**
 * Created by Administrator on 2015/9/19 0019.
 */
public class OnAddDrugItemEvent extends  BaseOnEvent
{
    public List<DrugItem> mDrugItems;

    public OnAddDrugItemEvent(List<DrugItem> drugItems)
    {
        mDrugItems = drugItems;
    }
}
