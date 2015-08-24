package com.xinheng.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.xinheng.mvp.model.ResultItem;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 使用Gson 统一处理json字符串和对象之间的转化
 */
public class GsonUtils
{
    /**
     * 将json字符串转化为一个对象
     *
     * @param json     ：json字符串
     * @param classOfT ：对象的Class
     * @param <T>      要转化的对象
     * @return null 或者 一个T类型的对象
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
            System.out.println("json to class【" + classOfT + "】 解析失败  " + e.getMessage());
        }
        System.out.println("\n" + json);
        return t;
    }

    /**
     * 将json字符串转化为一个对象列表
     *
     * @param json    ：json字符串
     * @param typeOfT ：对象列表的 type
     * @param <T>     要转化的对象
     * @return null 或者 一个对象列表
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
            System.out.println("json to list 解析失败  " + e.getMessage());
        }
        System.out.println("\n" + json);
        return items;
    }

    private static <T> List<T> jsonToList(Object obj, Type typeOfT)
    {
        List<T> items = null;
        if (obj != null)
        {
            items = jsonToList(obj.toString(), typeOfT);
        }
        return items;
    }

    /**
     * 将一个json字符串转化为一个List集合，中间经过一次转化为{@link ResultItem}对象的处理
     *
     * @param json    :结果json字符串
     * @param typeOfT 对象列表的 type
     * @param <T>     要转化的对象
     * @return
     */
    public static <T> List<T> jsonToResultItemToList(String json, Type typeOfT)
    {
        List<T> items = null;
        try
        {
            ResultItem resultItem = GsonUtils.jsonToClass(json, ResultItem.class);
            if (null != resultItem)
            {
                items = jsonToList(resultItem.properties, typeOfT);
            }
        }
        catch (Exception e)
        {
            System.out.println("ResultItemToList  解析失败  " + e.getMessage());
        }
        return items;
    }

    /**
     * 将一个{@link ResultItem}对象的{@link ResultItem#properties}字段转化为一个List集合，
     *
     * @param resultItem :ResultItem对象
     * @param typeOfT    对象列表的 type
     * @param <T>        要转化的对象
     * @return
     */
    public static <T> List<T> resultItemToList(ResultItem resultItem, Type typeOfT)
    {
        return jsonToList(resultItem.properties, typeOfT);

    }

    /**
     * 将一个对象转化成json字符串
     *
     * @param object
     * @return
     */
    public static String toJson(Object object)
    {
        return new Gson().toJson(object);
    }

}
