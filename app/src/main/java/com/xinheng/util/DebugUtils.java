package com.xinheng.util;

import android.content.Context;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/7/15 0015
 * 时间： 09:14
 * 说明：
 */
public class DebugUtils
{
    private static final boolean DEBUG = true;
    private static final boolean DEBUG_FRAGMENT=true;

    /**
     * 控制LogCat是否打印开发中的一些数据
     * @param context：android 上下文对象
     * @return 根据IMEI控制true、false
     */
    public static boolean isShowDebug(Context context)
    {
        boolean flag = DEBUG;
        return flag;
    }





}

