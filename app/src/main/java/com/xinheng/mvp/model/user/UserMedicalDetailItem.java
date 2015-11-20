package com.xinheng.mvp.model.user;

import com.xinheng.mvp.model.BaseEmptyItem;
import com.xinheng.mvp.model.ImageItem;

import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/21 0021
 * 时间： 16:18
 * 说明：  我的病历详情信息
 */
public class UserMedicalDetailItem extends BaseEmptyItem
{

    public String name;//患者姓名
    public String photo;//患者头像
    public String birthday;//出生年月
    public String sex;//性别
    public String seeDate;//就诊日期
    public MedicalRecord medicalrecord;
    public static final String DEBUG_SUCCESS = "{\"message\":\"获取信息成功！\",\"properties\":{\"birthday\":\"1381507200000\",\"medicalrecord\":{\"createTime\":\"1427817600000\",\"department\":\"心内科1\",\"doctName\":\"宋宁静\",\"id\":\"cc62c8f0-4fa0-11e5-a588-71aa030d\",\"illnessimgs\":[],\"prescimgs\":[],\"record\":\"慢性化脓性鼻窦炎 常继发于急性化脓性鼻窦炎,以多脓涕为主要表现,可伴有轻重不一的鼻塞,头痛及嗅觉障碍,平时 注意锻炼身体,劳逸结合,衣着适度,多呼吸新鲜空气,避免鼻子干燥,不轻易滴用鼻药,对鼻腔病变及时诊治,邻近的病灶感染需治疗\",\"reportimgs\":[],\"type\":\"1\"},\"name\":\"周盼一\",\"photo\":\"32ea3826f632915f4a816080afe200a6887246b3\",\"seeDate\":\"1427817600000\",\"sex\":\"1\"},\"result\":\"1\"}";

    public static class MedicalRecord extends BaseEmptyItem
    {
        public String createTime;
        public String department;
        public String doctName;
        public String id;
        public List<ImageItem> illnessimg;
        public List<ImageItem> prescimg;
        public List<ImageItem> reportimg;
        public String record;
        public String type;
    }


}
