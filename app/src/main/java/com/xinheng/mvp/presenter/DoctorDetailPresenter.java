package com.xinheng.mvp.presenter;

/**
 * 获取医生详情接口
 */
public interface DoctorDetailPresenter
{
    /**
     * 根据医生id获取对应的医生详情
     * @param did
     */
    public void doGetDoctorDetail(String did);
}
