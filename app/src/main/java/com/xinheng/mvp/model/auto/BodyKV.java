package com.xinheng.mvp.model.auto;

import com.xinheng.mvp.model.BaseEmptyItem;

import java.util.List;

/**
 * 身体部位的KV
 */
public class BodyKV extends BaseEmptyItem
{
    public String key;
    public String value;

    /***
     * 身体部位的症状
     */
    public static class SymptomItem extends BaseEmptyItem
    {
        public String id;//症状id
        public String name;//症状名称
    }

    /***
     * 症状第一个问题
     */
    public static class SymptomQuestion extends BaseEmptyItem
    {
        public String flowId;//流程id
        public String qid;//问题id
        public String content;//问题内容
    }
    /***
     * 症状下一个问题
     */
    public static class SymptomNextQuestion extends BaseEmptyItem
    {
       public SymptomQuestion nextquestion;
    }
    public  static  class  SymptomResult extends  BaseEmptyItem
    {
       public SymptomExitItem exit;

    }
    public  static  class  SymptomExitItem extends  BaseEmptyItem
    {
        public List<SymptomDepart> depart;
        public  String result;
    }
    public  static  class SymptomDepart extends  BaseEmptyItem
    {
        public  String name;
    }
}
