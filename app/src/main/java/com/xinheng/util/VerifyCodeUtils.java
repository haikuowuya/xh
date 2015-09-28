package com.xinheng.util;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;

import com.xinheng.base.BaseActivity;

/**
 * Created by Steven on 2015/9/7 0007.
 */
public class VerifyCodeUtils
{
    private static CountDownTimer sCountDownTimer;

    public static void getVerifyCode(final BaseActivity activity, final Button button, long millisInFuture, String mobile)
    {
        new CountDownTimer(millisInFuture, 1000)
        {
            public void onTick(long millisUntilFinished)
            {
                if (!button.getText().toString().contains("成功") && button.isClickable())
                {
                    button.setClickable(false);//设置不能点击
                    String second = (int) (millisUntilFinished / 1000) + "";
                    button.setText(second + "秒后可重发");//设置倒计时时间
                    //设置按钮为灰色，这时是不能点击的
                    Spannable span = new SpannableString(button.getText().toString());//获取按钮的文字
                    span.setSpan(new ForegroundColorSpan(Color.RED), 0, second.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);//讲倒计时时间显示为红色
                    button.setText(span);
                } else
                {
                    cancel();
                    button.setText("验证码发送成功");

                }
            }

            @Override
            public void onFinish()
            {
                button.setText("重新获取验证码");
                button.setClickable(true);//重新获得点击
            }
        }.start();
    }
}
