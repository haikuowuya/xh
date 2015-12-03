package com.xinheng.mvp.model.doctor;

import com.xinheng.mvp.model.BaseEmptyItem;

import java.io.File;
import java.util.List;

/**
 * 获取在线咨询详情时向服务器端发送的post请求体
 */
public class PostOnlineCounselItem extends BaseEmptyItem
{
    public String userId;//用户id（加密）
    public String doctId;//医生id（加密）
    public String  sex;//性别  0:男 1：女
    public String  age;//年龄
    public String pathema;//疾病名称（加密）
    public String desc;//疾病描述（加密）
    public String  checkcode;//短信验证码
    public String mobile;//手机号码
    public String message;//患者留言
    public List<File> files;//图片组以文件流方式提交
}
