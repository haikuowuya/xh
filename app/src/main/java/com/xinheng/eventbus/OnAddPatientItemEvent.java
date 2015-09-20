package com.xinheng.eventbus;

import com.xinheng.mvp.model.user.UserPatientItem;

/**
 * 添加用户就诊人信息事件
 */
public class OnAddPatientItemEvent extends  BaseOnEvent
{
    public UserPatientItem mUserPatientItem;
    public OnAddPatientItemEvent(UserPatientItem userPatientItem)
    {
        mUserPatientItem = userPatientItem;
    }
}
