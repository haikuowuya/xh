package com.xinheng.mvp.model.user;

import com.xinheng.mvp.model.BaseEmptyItem;

import java.io.File;
import java.util.List;

/**
 * 添加处方post请求体
 */
public class PostAddRecipeItem extends BaseEmptyItem
{
    public String userId;//用户id（加密）
    public String institution;//处方开具医院
    public String prescTime;//处方开具时间
    public String name;//检查报告名称
    public List<File> files;//处方图片,多张，以文件流方式提交

}
