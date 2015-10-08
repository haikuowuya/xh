package com.xinheng.util;

import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.xinheng.R;
import com.xinheng.base.BaseActivity;

/**
 *
 */
public class OrderDetailPopupWindowUtils
{
    private static BottomSheetPopupWindow mPopupWindow;

    private static void initPopupWindow(BaseActivity activity)
    {
        mPopupWindow = new BottomSheetPopupWindow(activity);
        // 设置SelectPicPopupWindow弹出窗体的宽
        mPopupWindow.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        mPopupWindow.setHeight(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        mPopupWindow.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        mPopupWindow.setAnimationStyle(R.style.AnimBottom);
        // 设置SelectPicPopupWindow弹出窗体的背景
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0000000000));
    }

    public static void showOrderDetail(BaseActivity baseActivity, View contentView)
    {
        initPopupWindow(baseActivity);
        mPopupWindow.setContentView(contentView);
        mPopupWindow.showAtLocation(baseActivity.getContentViewGroup(), Gravity.BOTTOM, 0, 0);
    }

    public static void dismiss()
    {
        if (null != mPopupWindow && mPopupWindow.isShowing())
        {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }

}
