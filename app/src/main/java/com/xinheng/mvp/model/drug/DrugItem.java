package com.xinheng.mvp.model.drug;

import com.xinheng.mvp.model.BaseEmptyItem;

/**
 * 药品item
 */
public class DrugItem  extends BaseEmptyItem
{
    /***
     * 药品id
     */
   public String id;
    /***
     * 药品名称
     */
    public String name;
    /***
     * 药品图片
     */
    public String  img;
    /**
     * 药品单位
     */
    public String  unit;
    /***
     * 药品性质
     */
    public String  nature;
    /***
     * 药品生产商
     */
    public String producer;
    /***
     * 药品产地
     */
    public String  place;

 @Override
 public boolean equals(Object o)
 {
  if(null != o && o instanceof  DrugItem)
  {
   return  id.equals(((DrugItem)o).id );
  }
   return super.equals(o);
 }

 public static final String DEBUG_SUCCESS ="{\"result\":\"1\",\"message\":\"获取药品数据成功\",\"properties\":[{\"id\":\"40288af44ee35f3f014ee36081cf1000\",\"name\":\"养生八宝茶\",\"img\":\"/ad/20150512/001.jpg\",\"unit\":\"单位\",\"nature\":\"性质\",\"producer\":\"生产商\",\"place\":\"产地\"},{\"id\":\"40288af44ee35f3f014e45544cf2000\",\"name\":\"桂枝\",\"img\":\"/ad/20150512/001.jpg\",\"unit\":\"单位\",\"nature\":\"性质\",\"producer\":\"生产商\",\"place\":\"产地\"},{\"id\":\"40288af44ee35f3f014e45544cf2000\",\"name\":\"荆芥\",\"img\":\"/ad/20150512/001.jpg\",\"unit\":\"单位\",\"nature\":\"性质\"},{\"id\":\"40288af44ee35f3f01443445544cf2000\",\"title\":\"葛根\",\"img\":\"/ad/20150512/001.jpg\",\"unit\":\"单位\",\"nature\":\"性质\",\"producer\":\"生产商\",\"place\":\"产地\"},{\"id\":\"40288af44ee35f3f01443445544cf2000\",\"name\":\"药品1\",\"img\":\"/ad/20150512/001.jpg\",\"unit\":\"单位\",\"nature\":\"性质\",\"producer\":\"生产商\",\"place\":\"产地\"}]}";


}
