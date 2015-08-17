package com.xinheng.util;

import android.content.Intent;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/7/30 0030
 * 时间： 16:35
 * 说明：
 */
public class IntentUtils
{

    public static final boolean isLauncherIntent(Intent intent)
    {
        boolean flag = false;
        if (intent != null)
        {
            flag = Intent.ACTION_MAIN.equals(intent.getAction());
            if (null != intent.getCategories())
            {
                boolean category = false;
                for (String str : intent.getCategories())
                {
                    category = Intent.CATEGORY_LAUNCHER.equals(str);
                    if (category)
                    {
                        break;
                    }
                }
                flag = flag && category;
            }
        }
        return flag;
    }
}
