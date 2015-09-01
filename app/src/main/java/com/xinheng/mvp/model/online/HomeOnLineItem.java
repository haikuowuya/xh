package com.xinheng.mvp.model.online;

import com.xinheng.mvp.model.AdItem;
import com.xinheng.mvp.model.BaseEmptyItem;

import java.util.List;

/**
 *  在线售药的首页数据
 */
public class HomeOnLineItem extends BaseEmptyItem
{
    public static final String  KEY_ADVERTISEMENT="advertisement";
    public static final String KEY_SUBJECT="subject";

    public static final String KEY_LIST="list";

    /**
     * 顶部的广告
     */
    public List<AdItem> advertisement;
    /**
     * 中间数据
     */
    public List<OnLineCenterItem> subject;
    /**
     * 底部数据
     */
    public List<OnLineBottomItem> list;

    /**
     * 在线售药首界面中间和底部每一个Item的数据
     */
    public static class  Item
    {
        public  String id;
        public String drugId;
        public  String title;
        public  String img;
    }

    @Override
    public String toString()
    {

        StringBuffer stringBuffer = new StringBuffer();

        if(null != advertisement && !advertisement.isEmpty())
        {
            stringBuffer.append("advertisement.size = " + advertisement.size() );
            for(AdItem adItem : advertisement)
            {
                 stringBuffer.append(" adItem = " + adItem);
            }
        }

        if(null != subject && !subject.isEmpty())
        {
            stringBuffer.append(" subject.size = " + subject.size() );
            for(OnLineCenterItem centerItem : subject)
            {
                stringBuffer.append(" centerItem = " + centerItem);
            }
        }

        if(null != list && !list.isEmpty())
        {
            stringBuffer.append(" list.size = " + list.size() );
            for(OnLineBottomItem bottomItem : list)
            {
                stringBuffer.append(" bottomItem = " + bottomItem);
            }
        }

























        return  stringBuffer.toString();
    }

    public static final String  DEBUG_SUCCESS="{\"result\":\"1\",\"message\":\"获取在线售药首页数据成功\",\"properties\":[{\"advertisement\":[{\"id\":\"40288af44ee35f3f014ee36081cf1000\",\"title\":\"0元换健康\",\"img\":\"/ad/20150512/001.jpg\"},{\"id\":\"40288af44ee35f3f014e45544cf2000\",\"title\":\"0元换健康2\",\"img\":\"/ad/20150512/001.jpg\"},{\"id\":\"40288af44ee35f3f014e45544cf2000\",\"title\":\"0元换健康3\",\"img\":\"/ad/20150512/001.jpg\"},{\"id\":\"40288af44ee35f3f01443445544cf2000\",\"title\":\"0元换健康4\",\"img\":\"/ad/20150512/001.jpg\"},{\"id\":\"40288af44ee35f3f01443445544cf2000\",\"title\":\"0元换健康5\",\"img\":\"/ad/20150512/001.jpg\"}]},{\"subject\":[{\"id\":\"1\",\"name\":\"养生汤料\",\"items\":[{\"id\":\"40288af44ee35f3f014ee36081cf1000\",\"drugId\":\"2\",\"title\":\"四君子汤\",\"img\":\"/ad/20150512/001.jpg\"},{\"id\":\"40288af44ee35f3f014e45544cf2000\",\"drugId\":\"2\",\"title\":\"八珍汤\",\"img\":\"/ad/20150512/001.jpg\"},{\"id\":\"40288af44ee35f3f014e45544cf2000\",\"drugId\":\"2\",\"title\":\"养胃汤\",\"img\":\"/ad/20150512/001.jpg\"}]}]},{\"list\":[{\"id\":\"1\",\"name\":\"解表药\",\"items\":[{\"id\":\"40288af44ee35f3f014ee36081cf1000\",\"drugId\":\"2\",\"title\":\"四君子汤\",\"img\":\"/ad/20150512/001.jpg\"},{\"id\":\"40288af44ee35f3f014e45544cf2000\",\"drugId\":\"2\",\"title\":\"八珍汤\",\"img\":\"/ad/20150512/001.jpg\"},{\"id\":\"40288af44ee35f3f014e45544cf2000\",\"drugId\":\"2\",\"title\":\"养胃汤\",\"img\":\"/ad/20150512/001.jpg\"}]}]}]}";
    public static final String DEBUG_FAILURED="{\"result\":\"-1\",\"message\":\"获取在线售药首页数据失败\"}";
}
