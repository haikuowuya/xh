package com.xinheng.eventbus;

/**
 * Created by Steven on 2015/9/30 0030.
 */
public class OnModifyNicknameEvent extends  BaseOnEvent
{
    public  String newNickname;

    public OnModifyNicknameEvent(String newNickname)
    {
        this.newNickname = newNickname;
    }
}
