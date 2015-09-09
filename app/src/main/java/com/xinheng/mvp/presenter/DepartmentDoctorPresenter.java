package com.xinheng.mvp.presenter;

/**
 *
 * 获取科室中的医生列表信息接口
 * */
public interface DepartmentDoctorPresenter
{

    /**
     * 根据科室id获取对应的医生列表
     * @param departId     ：科室id
     */
    public void doGetDepartDoctorList(String departId);
}
