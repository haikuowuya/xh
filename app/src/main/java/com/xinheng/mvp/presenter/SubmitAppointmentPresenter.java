package com.xinheng.mvp.presenter;

import com.xinheng.mvp.model.appointment.PostSubmitAppointmentItem;

/**
 * 提交预约挂号的接口
 */
public interface SubmitAppointmentPresenter
{
    public void doSubmitSubscribe(PostSubmitAppointmentItem item);
}
