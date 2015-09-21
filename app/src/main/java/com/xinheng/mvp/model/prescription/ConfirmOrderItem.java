package com.xinheng.mvp.model.prescription;

import com.xinheng.mvp.model.BaseEmptyItem;

import java.util.List;

/**
 * 确认订单返回的数据实体类
 */
public class ConfirmOrderItem extends BaseEmptyItem
{
    public String id;//订单id
    public String fee;//总价（不包括配送费用）
    public String quantity;//剂数
    public List<ListItem> list;

    public static class ListItem extends BaseEmptyItem
    {
        public String id;//药品id
        public String img;//药品图片
        public String name;//药品名称
        public String number;//药品数量
        public String producer;//药品产地
        public String specs;//药品包装规格
        public String unit;//药品包装单位
    }

    public static final String  DEBUG_SUCCESS="{\"message\":\"获取信息成功!\",\"properties\":{\"id\":\"40288af44f7870fa014f78a22e98001d\",\"fee\":\"52.5\",\"list\":[{\"id\":\"40288af44f7870fa014f78a22e98001d\",\"img\":\"/uploads/20150829/1650291440838215175.png\",\"name\":\"炒白术\",\"number\":\"10\",\"producer\":\"上海\",\"specs\":\"15g/袋\",\"unit\":\"袋\"}],\"quantity\":\"3\"},\"result\":\"1\"}";
}
