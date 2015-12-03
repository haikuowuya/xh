package com.xinheng.eventbus;

import com.xinheng.mvp.model.user.UserPatientItem;

/**
 *  选择病人事件
 */
public class OnSelectPatientEvent extends BaseOnEvent
{
    public UserPatientItem mUserPatientItem;

    public OnSelectPatientEvent(UserPatientItem patientItem)
    {
        mUserPatientItem = patientItem;
    }
}
