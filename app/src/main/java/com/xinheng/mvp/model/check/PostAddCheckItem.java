package com.xinheng.mvp.model.check;

import com.xinheng.mvp.model.BaseEmptyItem;

/**
 * 6.2预约检查-post提交的请求体
 */
public class PostAddCheckItem extends BaseEmptyItem
{
    public String userId;//用户id
    public String sex;//检查人性别
    public String age;//检查人年龄
    public String departId;//科室id
    public String checkId;//检查项目id
    public String checkTime;//检查时间
    public String checkcode;//短信验证码
    public String mobile;// 手机号码
}
