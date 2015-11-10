package com.xinheng.mvp.presenter;

import com.xinheng.mvp.model.prescription.PostPayDespatchItem;

import java.util.List;

/**
 * 购物车列表接口
 */
public interface ShoppingCartPresenter
{
    public void doGetShoppingCartList();

    /***
     * 将商品添加到购物车
     *
     * @param drugId ：药品id
     * @param count  :数量
     */
    public void doAddToShoppingCart(String drugId, String count);

    /***
     * 编辑保存购物车
     *
     * @param drugIds
     * @param counts
     */
    public void doEditSaveShoppingCart(List<String> drugIds, List<String> counts);

    public void subimtShoppingCart(PostPayDespatchItem PostPayDespatchItem);

}
