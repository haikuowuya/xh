package com.xinheng.eventbus;

/**
 * 修改昵称事件
 */
public class OnModifyNicknameEvent extends  BaseOnEvent
{
    public  String newNickname;

    public OnModifyNicknameEvent(String newNickname)
    {
        this.newNickname = newNickname;
    }
}
