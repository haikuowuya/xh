package com.xinheng.mvp.model.prescription;

import com.xinheng.mvp.model.BaseEmptyItem;

import java.util.List;

/**
 * 提交订单时向服务器端发送的post请求体
 */
public class PostPayDespatchItem extends BaseEmptyItem
{

    public static final String PAY_ONLINE_TEXT = "在线支付";
    public static final String PAY_OFFLINE_TEXT = "货到付款";
    public static final String PAY_ACCOUNT_TEXT = "余额扣款";
    public static final String DESPATCH_NORMA_TEXT = "普通快递";
    public static final String DESPATCH_SELF_TEXT = "门店自提";

    public static final String PAY_ACCOUNT = "2";
    public static final String PAY_ONLINE = "1";
    public static final String PAY_OFFLINE = "0";
    public static final String DESPATCH_NORMAL = "1";
    public static final String DESPATCH_SELF = "0";
    public String id;//订单id
    public String aid;//收货地址id
    public String payType = PAY_ONLINE;//支付方式
    public String despatchType = DESPATCH_NORMAL;//配送方式
    public String userId;


    public List<ListItem> list;
    public static class ListItem
    {
        public String hid;  //医疗机构id
        public List<String> shoppingIds;  //购物车id
        public List<String> drugIds;//药品id
        public List<String> counts;//药品数量
    }

}
