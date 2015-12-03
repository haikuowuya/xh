package com.xinheng.eventbus;

/**
 * 修改手机号码事件
 */
public class OnModifyPhotoEvent extends BaseOnEvent
{
    public String mImageFilePath;

    public OnModifyPhotoEvent(String imageFilePath)
    {
        this.mImageFilePath = imageFilePath;
    }
}
