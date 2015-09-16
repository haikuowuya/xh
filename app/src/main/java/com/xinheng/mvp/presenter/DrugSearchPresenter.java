package com.xinheng.mvp.presenter;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/26 0026
 * 时间： 17:08
 * 说明： 搜索药品接口
 */
public interface DrugSearchPresenter
{
    /**
     *  @param  hid: 医院id
     * @param keyword：搜索关键字
     */
    public void doSearchDrug(String hid, String keyword);
}
