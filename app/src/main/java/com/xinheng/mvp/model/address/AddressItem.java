package com.xinheng.mvp.model.address;

import com.xinheng.mvp.model.BaseEmptyItem;

/**
 * Created by Administrator on 2015/9/20 0020.
 */
public class AddressItem extends BaseEmptyItem
{
    public String id;//收货地址id
    public String cityCode;//城市编码
    public String name;//收货人姓名
    public String mobile;//收货人电话
    public String zipcode;//邮政编码
    public String address;//详细地址
    public String email;//邮件
    public String qq;//联系qq
    public String isDefault;//是否默认地址 0 否 1 是
    public String createTime;//创建时间
    public String city;
}
