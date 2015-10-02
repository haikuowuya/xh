package com.xinheng.eventbus;

/**
 * Created by Steven on 2015/9/30 0030.
 */
public class OnModifyAccountNameEvent extends BaseOnEvent
{
    public String mNewAccountName;

    public OnModifyAccountNameEvent(String newAccountName)
    {
        this.mNewAccountName = newAccountName;
    }
}
