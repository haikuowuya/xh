package com.xinheng.mvp.presenter;

import com.xinheng.mvp.model.address.PostAddressItem;

/**
 * 修改or添加收货地址
 * 根据{@link PostAddressItem#id}提交体中收货地址id是否为空进行判断是添加还是修改
 */
public interface AddOrModifyAddressPresenter
{
    public  void  doAddOrModifyAddress(PostAddressItem item);
}
