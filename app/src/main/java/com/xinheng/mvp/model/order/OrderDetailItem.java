package com.xinheng.mvp.model.order;

import com.google.gson.JsonElement;
import com.xinheng.mvp.model.BaseEmptyItem;

import java.util.List;

/**
 * Created by Administrator on 2015/10/9 0009.
 */
public class OrderDetailItem extends BaseEmptyItem
{
    public JsonElement address;
    public String orderId;//订单id
    public String hid;//医疗机构或药店id
    public String hospital;//医疗机构名称
    public String orderStatus;//订单状态
    public String createTime;//订单创建时间
    public String fee;//订单总价格

    public String despatchFee;
    public String despatchType;
    public String payType;
    public String quantity;
    public List<ListItem> orderList;

    public static class ListItem extends BaseEmptyItem
    {
        public String drugId;//药品id
        public String drugImg;//药品图片
        public String drugName;//药品名称
        public String count;//药品数量
        public String producer;//药品产地
        public String specs;//药品包装规格
        public String unit;//药品包装单位
        public String unitPrice;//药品包装价格
    }

}
