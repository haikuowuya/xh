package com.xinheng.mvp.model.appointment;

import com.xinheng.mvp.model.PostItem;

/**
 *  获取我的预约挂号/预约加号列表时向服务器端发送的post请求体
 */
public class PostUserAppointmentItem extends PostItem
{
    /**
     * 预约类型（预约挂号/预约加号）
     */
    public String type;
}
