package com.xinheng.mvp.presenter;

/**
 *  购物车列表接口
 */
public interface ShoppingCartPresenter
{
    public  void  doGetShoppingCartList();

    /***
     * 将商品添加到购物车
     * @param drugId  ：药品id
     * @param count  :数量
     */
    public void doAddToShoppingCart(String drugId,String count );

}
