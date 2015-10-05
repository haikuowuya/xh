package com.xinheng.mvp.presenter;

import com.xinheng.mvp.model.user.PostAddMedicalRecordItem;

/**
 * 提交添加病历的接口
 */
public interface SubmitAddMedicalRecordPresenter
{
    public void doAddMedicalRecord(  PostAddMedicalRecordItem postAddMedicalRecordItem);
}
