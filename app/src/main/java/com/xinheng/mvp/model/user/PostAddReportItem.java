package com.xinheng.mvp.model.user;

import com.xinheng.mvp.model.BaseEmptyItem;

import java.io.File;
import java.util.List;

/**
 * 添加报告post请求体
 */
public class PostAddReportItem   extends BaseEmptyItem
{
  public String userId;//用户id（加密）
    public String  institution;//医疗机构
    public String   checkTime;//检查时间
    public String   name;//检查报告名称
    public List<File> files;//检查报告图片,多张，以文件流方式提交
}
