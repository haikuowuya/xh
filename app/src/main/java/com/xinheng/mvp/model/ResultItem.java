package com.xinheng.mvp.model;

import com.google.gson.JsonElement;

/**
 * Created by Administrator on 2015/8/20 0020.
 */
public class ResultItem extends  BaseEmptyItem
{
    /***
     * 用户获取数据成功时服务器端返回的状态值
     */
    public static final String RESULT_SUCCESS="1";
//    public static final String RESULT_FAILURED="-1";
    public static  final  String RESULT_SESSION_EXPIRED ="-4" ;
    public String  result;
    public String  message;
    public JsonElement properties;

    /***
     * 判断当前获取到的数据是否是正确的(正常的逻辑操作，比如注册成功才是正确的)
     * @return
     */
    public boolean success()
    {
        boolean flag = RESULT_SUCCESS.equals(result)  ;
        return flag;
    }

    /***
     * session 是否过期
     * @return
     */
    public  boolean sessionIsExpired()
    {
        boolean flag = RESULT_SESSION_EXPIRED.equals(result)  ;
        return flag;

    }


}
