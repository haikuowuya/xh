package com.xinheng.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Steven on 2015/9/11 0011.
 */
public class DateFormatUtils
{
    public static String format(String time)
    {
        return format(time, false)    ;
    }

    public static String format(String time, boolean withYMD)
    {
        return  format(time,withYMD,true);
    }

    /**
     * 格式化时间
     * @param time ：long字符串类型的时间
     * @param withYMD
     * @param withHm
     * @return
     */
    public static String format(String time, boolean withYMD,boolean withHm )
    {
        Date date = new Date(Long.parseLong(time));
        String pattern = "MM-dd HH:mm";
        if (withYMD )
        {
            pattern = "yyyy-MM-dd";
            if(withHm)
            {
                pattern = "yyyy-MM-dd HH:mm";
            }
        }
        return new SimpleDateFormat(pattern).format(date);
    }
}
