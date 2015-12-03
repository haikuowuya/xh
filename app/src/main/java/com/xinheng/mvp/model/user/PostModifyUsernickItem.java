package com.xinheng.mvp.model.user;

import com.xinheng.mvp.model.PostItem;

/**
 * 修改用户昵称时向服务器端发送的post请求体
 */
public class PostModifyUsernickItem extends PostItem
{
    public  String  nickname;
}
