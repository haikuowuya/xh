package com.xinheng.util;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.xinheng.R;
import com.xinheng.UserOrderActivity;
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
        //设置点击窗口外边窗口消失
        mPopupWindow.setOutsideTouchable(false);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        mPopupWindow.setAnimationStyle(R.style.AnimBottom);
        // 设置SelectPicPopupWindow弹出窗体的背景
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0000000000));
    }

    public static void showOrderDetail(final BaseActivity baseActivity, View contentView)
    {
        initPopupWindow(baseActivity);
        mPopupWindow.setContentView(contentView);
        mPopupWindow.showAtLocation(baseActivity.getContentViewGroup(), Gravity.BOTTOM, 0, 0);
        contentView.setFocusableInTouchMode(true);
        contentView.setFocusable(true);// 设置view能够接听事件，标注1
        contentView.setFocusableInTouchMode(true); // 设置view能够接听事件 标注2
        contentView.setOnKeyListener(
                new View.OnKeyListener()
                {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event)
                    {
                        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK)
                        {
                            new AlertDialog.Builder(baseActivity).setMessage("确定要放弃付款？").setPositiveButton(
                                    "确定", new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which)
                                        {
                                            dialog.dismiss();
                                            OrderDetailPopupWindowUtils.dismiss();
                                            UserOrderActivity.actionUserOrder(baseActivity, 3);
                                            baseActivity.finish();
                                        }
                                    }).setNegativeButton(
                                    "取消", new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which)
                                        {
                                            dialog.dismiss();
                                        }
                                    }).show();
                            return true;
                        }
                        return false;
                    }
                });


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
