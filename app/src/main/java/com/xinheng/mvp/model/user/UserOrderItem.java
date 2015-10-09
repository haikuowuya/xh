package com.xinheng.mvp.model.user;

import com.xinheng.mvp.model.BaseEmptyItem;

import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/20 0020
 * 时间： 16:59
 * 说明：我的订单信息
 */
public class UserOrderItem extends BaseEmptyItem
{

    public static final String DEBUG_SUCCESS = "{\"message\":\"获取数据成功\",\"properties\":[{\"alipayTradeNo\":\"123456789\",\"createTime\":\"1438133859000\",\"despatchtime\":\"1438133877000\",\"fee\":\"100.36\",\"hid\":\"402881b44e570757014e570ab37e0000\",\"hospital\":\"贵州省中医医院\",\"orderId\":\"1\",\"orderStatus\":\"1\",\"orderlist\":[{\"count\":\"1\",\"drugId\":\"1\",\"drugName\":\"药品11\",\"place\":\"上海\",\"producer\":\"苏州中药厂\",\"specs\":\"10*200\",\"unitPrice\":\"100.0\"}]},{\"alipayTradeNo\":\"123456789\",\"createTime\":\"1438133859000\",\"despatchtime\":\"1438133877000\",\"fee\":\"100.36\",\"hid\":\"402881b44e570757014e570ab37e0000\",\"hospital\":\"贵州省中医医院\",\"orderId\":\"1\",\"orderStatus\":\"1\",\"orderlist\":[{\"count\":\"1\",\"drugId\":\"1\",\"drugName\":\"药品11\",\"place\":\"上海\",\"producer\":\"苏州中药厂\",\"specs\":\"10*200\",\"unitPrice\":\"100.0\"}]},{\"alipayTradeNo\":\"123456789\",\"createTime\":\"1438133859000\",\"despatchtime\":\"1438133877000\",\"fee\":\"100.36\",\"hid\":\"402881b44e570757014e570ab37e0000\",\"hospital\":\"贵州省中医医院\",\"orderId\":\"1\",\"orderStatus\":\"1\",\"orderlist\":[{\"count\":\"1\",\"drugId\":\"1\",\"drugName\":\"药品11\",\"place\":\"上海\",\"producer\":\"苏州中药厂\",\"specs\":\"10*200\",\"unitPrice\":\"100.0\"}]},{\"alipayTradeNo\":\"123456789\",\"createTime\":\"1438133859000\",\"despatchtime\":\"1438133877000\",\"fee\":\"100.36\",\"hid\":\"402881b44e570757014e570ab37e0000\",\"hospital\":\"贵州省中医医院\",\"orderId\":\"1\",\"orderStatus\":\"1\",\"orderlist\":[{\"count\":\"1\",\"drugId\":\"1\",\"drugName\":\"药品11\",\"place\":\"上海\",\"producer\":\"苏州中药厂\",\"specs\":\"10*200\",\"unitPrice\":\"100.0\"}]},{\"alipayTradeNo\":\"123456789\",\"createTime\":\"1438133859000\",\"despatchtime\":\"1438133877000\",\"fee\":\"100.36\",\"hid\":\"402881b44e570757014e570ab37e0000\",\"hospital\":\"贵州省中医医院\",\"orderId\":\"1\",\"orderStatus\":\"1\",\"orderlist\":[{\"count\":\"1\",\"drugId\":\"1\",\"drugName\":\"药品11\",\"place\":\"上海\",\"producer\":\"苏州中药厂\",\"specs\":\"10*200\",\"unitPrice\":\"100.0\"}]},{\"alipayTradeNo\":\"123456789\",\"createTime\":\"1438133859000\",\"despatchtime\":\"1438133877000\",\"fee\":\"100.36\",\"hid\":\"402881b44e570757014e570ab37e0000\",\"hospital\":\"贵州省中医医院\",\"orderId\":\"1\",\"orderStatus\":\"1\",\"orderlist\":[{\"count\":\"1\",\"drugId\":\"1\",\"drugName\":\"药品11\",\"place\":\"上海\",\"producer\":\"苏州中药厂\",\"specs\":\"10*200\",\"unitPrice\":\"100.0\"}]},{\"alipayTradeNo\":\"123456789\",\"createTime\":\"1438133859000\",\"despatchtime\":\"1438133877000\",\"fee\":\"100.36\",\"hid\":\"402881b44e570757014e570ab37e0000\",\"hospital\":\"贵州省中医医院\",\"orderId\":\"1\",\"orderStatus\":\"1\",\"orderlist\":[{\"count\":\"1\",\"drugId\":\"1\",\"drugName\":\"药品11\",\"place\":\"上海\",\"producer\":\"苏州中药厂\",\"specs\":\"10*200\",\"unitPrice\":\"100.0\"}]},{\"alipayTradeNo\":\"123456789\",\"createTime\":\"1438133859000\",\"despatchtime\":\"1438133877000\",\"fee\":\"100.36\",\"hid\":\"402881b44e570757014e570ab37e0000\",\"hospital\":\"贵州省中医医院\",\"orderId\":\"1\",\"orderStatus\":\"1\",\"orderlist\":[{\"count\":\"1\",\"drugId\":\"1\",\"drugName\":\"药品11\",\"place\":\"上海\",\"producer\":\"苏州中药厂\",\"specs\":\"10*200\",\"unitPrice\":\"100.0\"}]},{\"alipayTradeNo\":\"123456789\",\"createTime\":\"1438133859000\",\"despatchtime\":\"1438133877000\",\"fee\":\"100.36\",\"hid\":\"402881b44e570757014e570ab37e0000\",\"hospital\":\"贵州省中医医院\",\"orderId\":\"1\",\"orderStatus\":\"1\",\"orderlist\":[{\"count\":\"1\",\"drugId\":\"1\",\"drugName\":\"药品11\",\"place\":\"上海\",\"producer\":\"苏州中药厂\",\"specs\":\"10*200\",\"unitPrice\":\"100.0\"}]},{\"alipayTradeNo\":\"123456789\",\"createTime\":\"1438133859000\",\"despatchtime\":\"1438133877000\",\"fee\":\"100.36\",\"hid\":\"402881b44e570757014e570ab37e0000\",\"hospital\":\"贵州省中医医院\",\"orderId\":\"1\",\"orderStatus\":\"1\",\"orderlist\":[{\"count\":\"1\",\"drugId\":\"1\",\"drugName\":\"药品11\",\"place\":\"上海\",\"producer\":\"苏州中药厂\",\"specs\":\"10*200\",\"unitPrice\":\"100.0\"}]},{\"alipayTradeNo\":\"123456789\",\"createTime\":\"1438133859000\",\"despatchtime\":\"1438133877000\",\"fee\":\"100.36\",\"hid\":\"402881b44e570757014e570ab37e0000\",\"hospital\":\"贵州省中医医院\",\"orderId\":\"1\",\"orderStatus\":\"1\",\"orderlist\":[{\"count\":\"1\",\"drugId\":\"1\",\"drugName\":\"药品11\",\"place\":\"上海\",\"producer\":\"苏州中药厂\",\"specs\":\"10*200\",\"unitPrice\":\"100.0\"}]},{\"alipayTradeNo\":\"123456789\",\"createTime\":\"1438133859000\",\"despatchtime\":\"1438133877000\",\"fee\":\"100.36\",\"hid\":\"402881b44e570757014e570ab37e0000\",\"hospital\":\"贵州省中医医院\",\"orderId\":\"1\",\"orderStatus\":\"1\",\"orderlist\":[{\"count\":\"1\",\"drugId\":\"1\",\"drugName\":\"药品11\",\"place\":\"上海\",\"producer\":\"苏州中药厂\",\"specs\":\"10*200\",\"unitPrice\":\"100.0\"}]},{\"alipayTradeNo\":\"123456789\",\"createTime\":\"1438133859000\",\"despatchtime\":\"1438133877000\",\"fee\":\"100.36\",\"hid\":\"402881b44e570757014e570ab37e0000\",\"hospital\":\"贵州省中医医院\",\"orderId\":\"1\",\"orderStatus\":\"1\",\"orderlist\":[{\"count\":\"1\",\"drugId\":\"1\",\"drugName\":\"药品11\",\"place\":\"上海\",\"producer\":\"苏州中药厂\",\"specs\":\"10*200\",\"unitPrice\":\"100.0\"}]}],\"result\":\"1\"}";
    public static final String DEBUG_FAILURED = "{\"result\":\"-1\",\"message\":\"获取订单信息列表失败！\"}";

    public static final String ORDER_STATUS_ALL = "-1";
    /**
     * 未提交审核
     */
    public static final String ORDER_STATUS_0 = "0";
    /**
     * 审核中
     */
    public static final String ORDER_STATUS_1 = "1";
    /**
     * 审核不通过
     */
    public static final String ORDER_STATUS_2 = "2";

    /**
     * 未付款
     */
    public static final String ORDER_STATUS_3 = "3";

    /**
     * 已经付款
     */
    public static final String ORDER_STATUS_4 = "4";

    /**
     * 退款成功
     */
    public static final String ORDER_STATUS_5 = "5";

    /**
     * 退款失败
     */
    public static final String ORDER_STATUS_6 = "6";

    /**
     * 交易完成
     */
    public static final String ORDER_STATUS_7 = "7";
    /**
     * 支付编号
     */
    public String alipayTradeNo;
    /**
     * 送达时间
     */
    public String despatchtime;
    /**
     * 订单id
     */
    public String orderId;
    /**
     * 医疗机构或药店id
     */
    public String hid;
    /**
     * 医疗机构名称
     */
    public String hospital;
    /**
     * 订单状态
     * 0:未付款，1：已付款， 2：已关闭
     * 3：已发药  4：申请退款 5：退款成功 6：退款失败 7：交易完成，可选参数，默认全部
     */
    public String orderStatus;
    /***
     * 订单创建时间
     */
    public String createTime;
    /**
     * 订单总价格
     */
    public String fee;
    /**
     * 订单中药品信息
     */
    public List<OrderMedicalItem> orderList;

    /***
     * 订单中的药品列表信息
     */
    public static class OrderMedicalItem extends BaseEmptyItem
    {

        /**
         * 药品id
         */
        public String drugId;
        /**
         * 药品名称
         */
        public String drugName;

        /**
         * 药品图片
         */
        public  String drugImg;
        /**
         * 药品单价
         */
        public String unitPrice;
        /**
         * 药品数量
         */
        public String count;
        /***
         * 药品规格
         */
        public String specs;
        /***
         * 药品生产厂家
         */
        public String producer;
        /***
         * 药品产地
         */
        public String place;

    }

    @Override
    public boolean equals(Object o)
    {
        if (null != o && o instanceof UserOrderItem)
        {
            UserOrderItem obj = (UserOrderItem) o;
            return  orderId.equals(obj.orderId);
        }
        return super.equals(o);
    }

    @Override
    public String toString()
    {

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("hospital = " + hospital + " fee = " + fee);
        if (null != orderList && orderList.size() > 0)
        {
            stringBuffer.append(" orderlist.size = " + orderList.size());
        }
        return stringBuffer.toString();
    }
}
