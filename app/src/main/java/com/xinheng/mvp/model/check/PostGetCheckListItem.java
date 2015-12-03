package com.xinheng.mvp.model.check;

import com.xinheng.mvp.model.PostItem;

/**
 * 获取我的检查列表时向服务器端发送的post请求体
 */
public class PostGetCheckListItem extends PostItem
{
    public  String hid;// 医院id
}
