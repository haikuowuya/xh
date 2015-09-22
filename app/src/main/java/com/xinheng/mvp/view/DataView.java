package com.xinheng.mvp.view;

import com.xinheng.mvp.model.ResultItem;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/20 0020
 * 时间： 15:51
 * 说明：
 */
public interface DataView
{
    public void onGetDataSuccess(ResultItem resultItem,String requestTag);
    public void onGetDataFailured(String msg,String requestTag);
}
