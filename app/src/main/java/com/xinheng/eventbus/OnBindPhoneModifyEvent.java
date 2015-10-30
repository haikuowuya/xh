package com.xinheng.eventbus;

/**
 * Created by Steven on 2015/9/30 0030.
 */
public class OnBindPhoneModifyEvent extends BaseOnEvent
{
    public String mNewBindPhone;

    public OnBindPhoneModifyEvent(String newBindPhone)
    {
        this.mNewBindPhone = newBindPhone;
    }
}
