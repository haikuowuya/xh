package com.xinheng.eventbus;

/**
 * 绑定手机号码事件
 */
public class OnBindPhoneModifyEvent extends BaseOnEvent
{
    public String mNewBindPhone;

    public OnBindPhoneModifyEvent(String newBindPhone)
    {
        this.mNewBindPhone = newBindPhone;
    }
}
