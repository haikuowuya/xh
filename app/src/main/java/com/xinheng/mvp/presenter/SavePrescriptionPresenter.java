package com.xinheng.mvp.presenter;

import com.xinheng.mvp.model.prescription.PostSavePrescriptionItem;

/**
 * 用户保存药方接口
 */
public interface SavePrescriptionPresenter
{
    public void doSavePrescription(PostSavePrescriptionItem item );
}
