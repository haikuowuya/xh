package com.xinheng.util;

import android.text.TextUtils;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/19 0019
 * 时间： 13:39
 * 说明：
 */
public class SplitUtils
{
    public static String[] split(String text)
    {
        String[] strings = null;
        if(!TextUtils.isEmpty(text))
        {
            strings = text.split(Constants.SPLIT);
        }
        return  strings;
    }
}
