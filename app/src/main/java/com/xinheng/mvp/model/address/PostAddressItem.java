package com.xinheng.mvp.model.address;

/**
 * 向服务器端提交地址信息的post实体类
 */
public class PostAddressItem extends  AddressItem
{
    public String userId;
    public String province;//省
    public String  county;//区县
}
