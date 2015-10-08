package com.xinheng.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by raiyi-suzhou on 2015/7/9 0009.
 */
public class BottomSheetPopupWindow extends PopupWindow
{
    private Context mContext;
    private Window mWindow;

    public BottomSheetPopupWindow(Context context)
    {
        super(context);
        mContext = context;
    }

    public BottomSheetPopupWindow(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mContext = context;
    }

    public BottomSheetPopupWindow(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        mContext = context;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public BottomSheetPopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y)
    {
        super.showAtLocation(parent, gravity, x, y);
        setbackgroundAlpha(0.5f);
    }

    @Override
    public void showAsDropDown(View anchor)
    {
        super.showAsDropDown(anchor);
        setbackgroundAlpha(0.5f);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff)
    {
        super.showAsDropDown(anchor, xoff, yoff);
        setbackgroundAlpha(0.5f);
    }

    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity)
    {
        //super.showAsDropDown(anchor, xoff, yoff, gravity);
        setbackgroundAlpha(0.5f);
    }

    @Override
    public void dismiss()
    {
        super.dismiss();
        setbackgroundAlpha(1f);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void setbackgroundAlpha(float bgAlpha)
    {
        if (mWindow == null)
        {
            mWindow = ((Activity) mContext).getWindow();
        }
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        lp.alpha = bgAlpha;
        mWindow.setAttributes(lp);
    }
}
