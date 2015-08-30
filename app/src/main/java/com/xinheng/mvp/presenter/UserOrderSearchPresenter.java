package com.xinheng.mvp.presenter;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/26 0026
 * 时间： 17:08
 * 说明： 获取我的订单搜索结果列表
 */
public interface UserOrderSearchPresenter
{
    /**
     * 搜索我的订单
     *
     * @param keyword：搜索关键字
     * @param status        ：订单的状态
     */
    public void doGetUserOrderSearch(String keyword, String status);
}
