package com.xinheng.mvp.model.user;

import com.xinheng.mvp.model.BaseEmptyItem;

/**
 * 常用就诊人信息
 */
public class UserPatientItem extends BaseEmptyItem
{
    /***
     * 性别代号 ，1表示男
     */
    public static final String DEFAULT_MAN = "1";
    /***
     * 性别代号 ，0表示女
     */
    public static final String DEFAULT_WOMAN = "0";

    /***
     * 是否是默认代号 ， 1表示默认
     */
    public static final String IS_DEFAULT = "1";
    /***
     * 是否是默认代号 ， 0表示不是默认就诊人
     */
    public static final String NOT_DEFAULT = "0";
    public String id;//就诊人id
    public String name;//就诊人姓名
    public String idcard;//身份证号
    public String sex;//性别
    public String birthday;//生日
    public String age;//年龄
    public String mobile;//手机号
    public String isDefault;//是否为默认就诊人 0:不是 1:是
    public String userId;

    public static final String DEBUG_SUCCESS = "{\"message\":\"获取数据成功\",\"properties\":[{\"age\":\"5\",\"birthday\":\"1279555200000\",\"id\":\"1\",\"idcard\":\"320925199014235\",\"isDefault\":\"0\",\"mobile\":\"15850217019\",\"name\":\"lily\",\"sex\":\"1\"}],\"result\":\"1\"}";
}
