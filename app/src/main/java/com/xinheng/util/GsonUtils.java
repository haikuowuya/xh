package com.xinheng.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 使用Gson 统一处理json字符串和对象之间的转化
 */
public class GsonUtils
{
    /**
     * 将json字符串转化为一个对象
     * @param json    ：json字符串
     * @param classOfT    ：对象的Class
     * @param <T>
     * @return        null 或者 一个T类型的对象
     */
    public static <T> T jsonToClass(String json, Class<T> classOfT)
    {
        T t = null;
        try
        {
            t = new Gson().fromJson(json, classOfT);
        }
        catch (Exception e)
        {
            System.out.println("json to class 解析失败");
        }
        return t;
    }

    /**
     * 将json字符串转化为一个对象列表
     * @param json    ：json字符串
     * @param typeOfT   ：对象列表的 type
     * @param <T>
     * @return     null 或者 一个对象列表
     */
    public static <T> List<T> jsonToList(String json, Type typeOfT)
    {
        List<T> items = null;
        try
        {
            items = new Gson().fromJson(json, typeOfT);
        }
        catch (Exception e)
        {
            System.out.println("json to class 解析失败");
        }
        return items;
    }

    /**
     * 将一个对象转化成json字符串
     * @param object
     * @return
     */
    public static String toJson(Object object)
    {
        return new Gson().toJson(object);
    }

}
