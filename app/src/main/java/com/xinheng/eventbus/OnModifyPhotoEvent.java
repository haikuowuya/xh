package com.xinheng.eventbus;

/**
 * Created by Steven on 2015/9/30 0030.
 */
public class OnModifyPhotoEvent extends BaseOnEvent
{
    public String mImageFilePath;

    public OnModifyPhotoEvent(String imageFilePath)
    {
        this.mImageFilePath = imageFilePath;
    }
}
