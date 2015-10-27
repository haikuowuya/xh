package com.xinheng.mvp.model.user;

import com.xinheng.mvp.model.BaseEmptyItem;

import java.util.List;

/**
 * 我的检查详情
 */
public class UserCheckDetailItem extends BaseEmptyItem
{

    public String id;//检查id
    public String name;//检查名称
    public String patient;//患者姓名
    public String checkTime;//检查时间
    public String createTime;//创建时间
    public String doctor;//检查医生

    public String fee;//检查总价
    public String payState;//是否缴费 0未缴费 1已缴费
    public String type;//检查类型 0 医院同步检查 1 用户上传我的便捷检查

    public String department;//检查医生所属科室
    /***
     * 用户上传由此字段
     */
    public String consentState;//审核状态 0 审核中 1审核通过 2审核不通过

    public List<ListItem> list;

    public static class ListItem
    {
        public String id;//检查id
        public String name;//检查名称
        public String cost;//检查费用
    }
}
