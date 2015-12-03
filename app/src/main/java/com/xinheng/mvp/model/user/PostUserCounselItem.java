package com.xinheng.mvp.model.user;

import com.xinheng.mvp.model.BaseEmptyItem;

/**
 * 获取咨询信息时向服务器端发送的post请求体
 */
public class PostUserCounselItem extends BaseEmptyItem
{

    public String id;//咨询ID
    public String doctId;//咨询医生id
    public String question;//咨询问题
    public String  content;//咨询內容
}
