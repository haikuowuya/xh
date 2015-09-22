package com.xinheng.util;

import android.content.Context;

import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.InputStream;

public class AssetUtils
{

    /**
     * 默认文件名称后缀
     */
    private static final String DEFAULT_FILENAME_SUFFIXES = ".json";

    /***
     * 读取assets目录中的文件，转化为字符串， 默认后缀名是json
     * @param context
     * @param fileName    ：assets目录中的文件名称， 不带后缀
     * @return
     */
    public static String readAssetData(Context context, String fileName)
    {

        return readAssetData(context, fileName, DEFAULT_FILENAME_SUFFIXES);
    }

    private static String readAssetData(Context context, String fileName, String suffixes)
    {
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        try
        {
            inputStream = context.getAssets().open(fileName + suffixes);
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buff)) != -1)
            {
                stringBuffer.append(new String(buff, 0, len, HTTP.UTF_8));
            }
            return stringBuffer.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (inputStream != null)
            {
                try
                {
                    inputStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return stringBuffer.toString();
    }
}


