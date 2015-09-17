package com.xinheng.mvp.model.prescription;

import com.xinheng.mvp.model.BaseEmptyItem;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2015/9/16 0016.
 */
public class PostSavePrescriptionItem extends BaseEmptyItem
{
  public String  userId;//用户id
    public String   name;//药方名称
    public String    hosname;//医院名称
    public String    doctorname;//医生名称
    public String   patientname;//患者名称
    public String    result;//诊断结论
    public String    quantity;//剂数
    public List<String>    drugId;//药品id
    public List<String> drugQuantity;//药品数量
    public File file ;//药方图片以文件流方式提交
}
