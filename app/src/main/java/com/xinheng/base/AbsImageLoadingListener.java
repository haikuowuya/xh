package com.xinheng.base;

import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xinheng.R;

/**
 *
 */
public abstract class AbsImageLoadingListener  implements ImageLoadingListener
{
    private static final int DEFAULT_IMAGE = R.mipmap.ic_launcher;
    @Override
    public void onLoadingStarted(String imageUri, View view)
    {
        if(view instanceof ImageView)
        {
            ((ImageView)view).setImageResource(DEFAULT_IMAGE);
        }
    }

    @Override
    public void onLoadingFailed(String imageUri, View view, FailReason failReason)
    {
        if(view instanceof ImageView)
        {
            ((ImageView)view).setImageResource(DEFAULT_IMAGE);
        }
    }

    @Override
    public void onLoadingCancelled(String imageUri, View view)
    {
        if(view instanceof ImageView)
        {
            ((ImageView)view).setImageResource(DEFAULT_IMAGE);
        }
    }
}
