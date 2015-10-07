package com.xinheng.mvp.model.user;

import com.xinheng.mvp.model.BaseEmptyItem;

import java.util.List;

/**
 * 用户处方详情
 */
public class UserRecipeDetailItem extends BaseEmptyItem
{
    //type=1 用户上传处方
    public String prescimgs;//处方图片

    public String id;//处方id
    public String name;//处方名称
    public String patient;//患者姓名
    public String prescTime;//处方开具时间
    public String doctor;//开具处方医生
    public String institution;//开具处方医生所属医院
    public String department;//开具处方医生所属科室
    public RecipeItem prescription;//处方信息

    public  String type;//处方类型

    public static class RecipeItem extends BaseEmptyItem
    {
        public String cost; // 处方价格
        public String quantity;//处方剂数
        public List<ListItem> list;

    }

    public static class ListItem extends BaseEmptyItem
    {
        public String name;//处方药品名称
        public String num;//处方药品数目
        public String unit;//处方药品单位
    }
}
