package com.xinheng.mvp.model.check;

import com.xinheng.mvp.model.BaseEmptyItem;

import java.util.List;

/**
 * 便捷检查中的检查科室以及检查项目
 */
public class DepartCheckItem extends BaseEmptyItem
{
    public String deptId;//科室id
    public String deptName;//科室名称
    public List<CheckItem> checks;
    public static final String DEBUG_SUCCESS = "{\"result\":\"1\",\"message\":\"获取科室检查项目信息成功\",\"properties\":[{\"id\":\"40288af44ee35f3f014ee36081cf1000\",\"name\":\"检验科\",\"item\":[{\"id\":\"40288af44ee35f3f014ee36081cf1000\",\"name\":\"血常规\"},{\"id\":\"40288af44ee35f3f014e45544cf2000\",\"name\":\"尿常规\"},{\"id\":\"40288af44ee35f3f014e45544cf2000\",\"name\":\"B超\"},{\"id\":\"40288af44ee35f3f01443445544cf2000\",\"name\":\"彩超\"}]},{\"id\":\"40288af44ee35f3f014ee36081cf43340\",\"name\":\"内分泌科\",\"item\":[{\"id\":\"40288af44ee35f3f014ee36081cf3000\",\"name\":\"透视\"},{\"id\":\"40288af44ee35f3f014e45544cf4000\",\"name\":\"超声波\"}]}]}";

    public static final class CheckItem extends BaseEmptyItem
    {
        public String id;//检查项目的id 对应非药品表的id
        public String name;//检查项目名称
        public String cost;//检查费用
    }
}
