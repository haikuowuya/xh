package com.xinheng.mvp.model.auto;

import com.xinheng.mvp.model.BaseEmptyItem;

/**
 * 向服务器端提交部位信息的post实体类
 */
public class PostBodypartItem extends BaseEmptyItem
{
    public String bodypart;//部位编码，接口4.1返回的key值
}
