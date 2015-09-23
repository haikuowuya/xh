package com.xinheng.eventbus;

import com.xinheng.mvp.model.user.UserPatientItem;

/**
 * Created by Steven on 2015/9/22 0022.
 */
public class OnSelectPatientEvent extends BaseOnEvent
{
    public UserPatientItem mUserPatientItem;

    public OnSelectPatientEvent(UserPatientItem patientItem)
    {
        mUserPatientItem = patientItem;
    }
}
