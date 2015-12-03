package com.xinheng.mvp.model;

import java.util.List;

/**
 * 购物车实体类
 */
public class ShoppingCartItem extends BaseEmptyItem
{
    public String hid;//医院id
    public String hospital;//医院名称
    public List<ListItem> list;//购物车中的药品列表

    public static class ListItem
    {
        public String drugId;// 药品id
        public String name;// 药品名称
        public String price;// 药品单价
        public String count;// 购买数量
        public String specs;// 药品规格
        public String producer;// 药品厂商
        public String drugImg;
        public String place;
        public  String shoppingId;// 购物车id

        @Override
        public boolean equals(Object o)
        {
            if (o instanceof ListItem)
            {
                ListItem listItem = (ListItem) o;
                return listItem.drugId.equals(drugId);
            }
            return super.equals(o);
        }
    }
}
