package com.xinheng.mvp.presenter;

import com.xinheng.mvp.model.UserDoctorDetailItem;

/**
 * Created by Steven on 2015/9/8 0008.
 */
public interface DoctorEvaluationPresenter
{
    /**
     * 提交医生评价
     *
     * @param userDoctorDetailItem 医生详情
     * @param serviceDescribe      服务描述
     * @param effectDescribe       疗效描述
     * @param feeDescribe          费用描述
     */
    public void doSubmitDoctorEvaluation(UserDoctorDetailItem userDoctorDetailItem, String serviceDescribe, String effectDescribe, String feeDescribe);

    /***
     * 获取医生评价详情
     */
    public void doGetDoctorEvaluationDetail(String doctId);
}
