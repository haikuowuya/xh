package com.xinheng.mvp.model.online;

import com.xinheng.mvp.model.AdItem;
import com.xinheng.mvp.model.BaseEmptyItem;

import java.util.List;

/**
 * 在线售药的首页数据
 */
public class HomeOnLineItem extends BaseEmptyItem
{
    public static final String KEY_ADVERTISEMENT = "advertisement";
    public static final String KEY_SUBJECT = "subject";

    public static final String KEY_LIST = "list";

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
    public static class Item
    {
        public String id;
        public String drugId;
        public String title;
        public String img;
    }

    @Override
    public String toString()
    {

        StringBuffer stringBuffer = new StringBuffer();

        if (null != advertisement && !advertisement.isEmpty())
        {
            stringBuffer.append("advertisement.size = " + advertisement.size());
            for (AdItem adItem : advertisement)
            {
                stringBuffer.append(" adItem = " + adItem);
            }
        }

        if (null != subject && !subject.isEmpty())
        {
            stringBuffer.append(" subject.size = " + subject.size());
            for (OnLineCenterItem centerItem : subject)
            {
                stringBuffer.append(" centerItem = " + centerItem);
            }
        }

        if (null != list && !list.isEmpty())
        {
            stringBuffer.append(" list.size = " + list.size());
            for (OnLineBottomItem bottomItem : list)
            {
                stringBuffer.append(" bottomItem = " + bottomItem);
            }
        }

        return stringBuffer.toString();
    }

    public static final String DEBUG_SUCCESS = "{\"message\":\"获取首页数据成功!\",\"properties\":{\"advertisement\":[{\"id\":\"1\",\"img\":\"/uploads/1.png\",\"title\":\"test1\"},{\"id\":\"2\",\"img\":\"/uploads/2.png\",\"title\":\"test2\"}],\"list\":[{\"id\":\"402882f84f4e01da014f4e02ca420000\",\"items\":[{\"drugId\":\"402882f84f4e01da014f4e02ca420000\",\"id\":\"0b44984d500ed9d701500f3080920010\",\"title\":\"通草\"},{\"drugId\":\"402882f84f4e01da014f4e02ca420000\",\"id\":\"0b44984d500ed9d701500f3067bb000f\",\"title\":\"王不留行\"},{\"drugId\":\"402882f84f4e01da014f4e02ca420000\",\"id\":\"40288af44f815794014f8169d5fd000a\",\"img\":\"/uploads/20150831/0940311440985241061.jpg\",\"title\":\"枳壳\"},{\"drugId\":\"402882f84f4e01da014f4e02ca420000\",\"id\":\"40288af44f811f47014f812148b4000f\",\"title\":\"川芎\"},{\"drugId\":\"402882f84f4e01da014f4e02ca420000\",\"id\":\"40288af44f811f47014f8120d1f50006\",\"title\":\"酒萸肉\"},{\"drugId\":\"402882f84f4e01da014f4e02ca420000\",\"id\":\"40288af44f811f47014f8120c6eb0005\",\"title\":\"地黄\"}],\"name\":\"化湿药\"},{\"id\":\"402882f84f4e2a44014f4e5423ca0008\",\"items\":[{\"drugId\":\"402882f84f4e2a44014f4e5423ca0008\",\"id\":\"0b44984d500ed9d701500f30eb050012\",\"title\":\"陈皮\"},{\"drugId\":\"402882f84f4e2a44014f4e5423ca0008\",\"id\":\"0b44984d500ed9d701500f300ff5000e\",\"title\":\"香附\"},{\"drugId\":\"402882f84f4e2a44014f4e5423ca0008\",\"id\":\"40288af44f815794014f816a2c7d000f\",\"title\":\"香附\"},{\"drugId\":\"402882f84f4e2a44014f4e5423ca0008\",\"id\":\"40288af44f815794014f81699b7c0009\",\"img\":\"/uploads/20150831/0940311440985226095.jpg\",\"title\":\"柴胡\"},{\"drugId\":\"402882f84f4e2a44014f4e5423ca0008\",\"id\":\"40288af44f811f47014f81211869000d\",\"title\":\"炒白术\"},{\"drugId\":\"402882f84f4e2a44014f4e5423ca0008\",\"id\":\"40288af44f811f47014f81210ff8000c\",\"title\":\"炒白芍\"}],\"name\":\"理气药\"}],\"subject\":[]},\"result\":\"1\"}";
    public static final String DEBUG_FAILURED = "{\"result\":\"-1\",\"message\":\"获取在线售药首页数据失败\"}";
}
