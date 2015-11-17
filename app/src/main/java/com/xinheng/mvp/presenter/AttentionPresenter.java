package com.xinheng.mvp.presenter;

/**
 *  科室医生-医生详情-添加关注、取消关注接口
 */
public interface AttentionPresenter
{
    public  void  doAddAttention(String doctId);
    public void doCancelAttention(String doctId);
}
