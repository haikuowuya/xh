package com.xinheng.mvp.model.check;

import com.xinheng.mvp.model.BaseEmptyItem;

/**
 * 获取我的症状信息时向服务器端发送的post请求体
 */
public class PostGetSymptomResultItem extends BaseEmptyItem
{
    public String flowId;//流程id
    public String answer;//用户回复 0：回答否, 1：回答是
}
