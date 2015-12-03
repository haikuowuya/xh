package com.xinheng.eventbus;

/**
 * 修改帐号名称事件
 */
public class OnModifyAccountNameEvent extends BaseOnEvent
{
    public String mNewAccountName;

    public OnModifyAccountNameEvent(String newAccountName)
    {
        this.mNewAccountName = newAccountName;
    }
}
