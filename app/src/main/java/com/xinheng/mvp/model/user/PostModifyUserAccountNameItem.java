package com.xinheng.mvp.model.user;

import com.xinheng.mvp.model.PostItem;

/**
 * 修改用户名称时向服务器端发送的post请求体
 */
public class PostModifyUserAccountNameItem extends PostItem
{
    public  String  account;
}
