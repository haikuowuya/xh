package com.xinheng.mvp.presenter;

/**
 * 根据患者id，医生id获取病历授权信息
 */
public interface PatientRecordPresenter
{
    public  void  doGetPatientRecord(String patientId, String doctId);
}
