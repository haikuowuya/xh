package com.xinheng.eventbus;

import com.xinheng.mvp.model.prescription.DrugItem;

import java.util.List;

/**
 * 添加药品事件
 */
public class OnAddDrugItemEvent extends  BaseOnEvent
{
    public List<DrugItem> mDrugItems;

    public OnAddDrugItemEvent(List<DrugItem> drugItems)
    {
        mDrugItems = drugItems;
    }
}
