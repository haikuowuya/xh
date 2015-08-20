package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import com.google.gson.reflect.TypeToken;
import com.xinheng.R;
import com.xinheng.adapter.user.SubscribeAdapter;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.UserSubscribeItem;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：
 */
public class UserSubscribeListFragment extends PTRListFragment implements DataView
{
    private static final String DATA = "{\n" +
            "    \"result\": \"1\",\n" +
            "    \"message\": \"获取预约信息成功\",\n" +
            "    \"propertise\": [\n" +
            "        {\n" +
            "            \"id\": \"327327237667777\",\n" +
            "            \"createTime\": \"22222222222\",\n" +
            "            \"doctId\": \"7\",\n" +
            "            \"doctorName\": \"朱震\",\n" +
            "            \"hospital\": \"苏州中医院\",\n" +
            "            \"department\": \"外科\",\n" +
            "            \"technicalPost\": \"主任医师\",\n" +
            "            \"appointmentTime\": \"78945489841445\",\n" +
            "            \"content\": \"根据您提交的病情描述及以往病历分析，初步诊断为扁桃体发炎、脓肿，建议就诊前先到医院检验科\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"327327237667777\",\n" +
            "            \"createTime\": \"22222222222\",\n" +
            "            \"doctId\": \"7\",\n" +
            "            \"doctorName\": \"朱震\",\n" +
            "            \"hospital\": \"苏州中医院\",\n" +
            "            \"department\": \"外科\",\n" +
            "            \"technicalPost\": \"主任医师\",\n" +
            "            \"appointmentTime\": \"78945489841445\",\n" +
            "            \"content\": \"根据您提交的病情描述及以往病历分析，初步诊断为扁桃体发炎、脓肿，建议就诊前先到医院检验科\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"327327237667777\",\n" +
            "            \"createTime\": \"22222222222\",\n" +
            "            \"doctId\": \"7\",\n" +
            "            \"doctorName\": \"朱震\",\n" +
            "            \"hospital\": \"苏州中医院\",\n" +
            "            \"department\": \"外科\",\n" +
            "            \"technicalPost\": \"主任医师\",\n" +
            "            \"appointmentTime\": \"78945489841445\",\n" +
            "            \"content\": \"根据您提交的病情描述及以往病历分析，初步诊断为扁桃体发炎、脓肿，建议就诊前先到医院检验科\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"327327237667777\",\n" +
            "            \"createTime\": \"22222222222\",\n" +
            "            \"doctId\": \"7\",\n" +
            "            \"doctorName\": \"朱震\",\n" +
            "            \"hospital\": \"苏州中医院\",\n" +
            "            \"department\": \"外科\",\n" +
            "            \"technicalPost\": \"主任医师\",\n" +
            "            \"appointmentTime\": \"78945489841445\",\n" +
            "            \"content\": \"根据您提交的病情描述及以往病历分析，初步诊断为扁桃体发炎、脓肿，建议就诊前先到医院检验科\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"327327237667777\",\n" +
            "            \"createTime\": \"22222222222\",\n" +
            "            \"doctId\": \"7\",\n" +
            "            \"doctorName\": \"朱震\",\n" +
            "            \"hospital\": \"苏州中医院\",\n" +
            "            \"department\": \"外科\",\n" +
            "            \"technicalPost\": \"主任医师\",\n" +
            "            \"appointmentTime\": \"78945489841445\",\n" +
            "            \"content\": \"根据您提交的病情描述及以往病历分析，初步诊断为扁桃体发炎、脓肿，建议就诊前先到医院检验科\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"327327237667777\",\n" +
            "            \"createTime\": \"22222222222\",\n" +
            "            \"doctId\": \"7\",\n" +
            "            \"doctorName\": \"朱震\",\n" +
            "            \"hospital\": \"苏州中医院\",\n" +
            "            \"department\": \"外科\",\n" +
            "            \"technicalPost\": \"主任医师\",\n" +
            "            \"appointmentTime\": \"78945489841445\",\n" +
            "            \"content\": \"根据您提交的病情描述及以往病历分析，初步诊断为扁桃体发炎、脓肿，建议就诊前先到医院检验科\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"327327237667777\",\n" +
            "            \"createTime\": \"22222222222\",\n" +
            "            \"doctId\": \"7\",\n" +
            "            \"doctorName\": \"朱震\",\n" +
            "            \"hospital\": \"苏州中医院\",\n" +
            "            \"department\": \"外科\",\n" +
            "            \"technicalPost\": \"主任医师\",\n" +
            "            \"appointmentTime\": \"78945489841445\",\n" +
            "            \"content\": \"根据您提交的病情描述及以往病历分析，初步诊断为扁桃体发炎、脓肿，建议就诊前先到医院检验科\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"327327237667777\",\n" +
            "            \"createTime\": \"22222222222\",\n" +
            "            \"doctId\": \"7\",\n" +
            "            \"doctorName\": \"朱震\",\n" +
            "            \"hospital\": \"苏州中医院\",\n" +
            "            \"department\": \"外科\",\n" +
            "            \"technicalPost\": \"主任医师\",\n" +
            "            \"appointmentTime\": \"78945489841445\",\n" +
            "            \"content\": \"根据您提交的病情描述及以往病历分析，初步诊断为扁桃体发炎、脓肿，建议就诊前先到医院检验科\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"327327237667777\",\n" +
            "            \"createTime\": \"22222222222\",\n" +
            "            \"doctId\": \"7\",\n" +
            "            \"doctorName\": \"朱震\",\n" +
            "            \"hospital\": \"苏州中医院\",\n" +
            "            \"department\": \"外科\",\n" +
            "            \"technicalPost\": \"主任医师\",\n" +
            "            \"appointmentTime\": \"78945489841445\",\n" +
            "            \"content\": \"根据您提交的病情描述及以往病历分析，初步诊断为扁桃体发炎、脓肿，建议就诊前先到医院检验科\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"327327237667777\",\n" +
            "            \"createTime\": \"22222222222\",\n" +
            "            \"doctId\": \"7\",\n" +
            "            \"doctorName\": \"朱震\",\n" +
            "            \"hospital\": \"苏州中医院\",\n" +
            "            \"department\": \"外科\",\n" +
            "            \"technicalPost\": \"主任医师\",\n" +
            "            \"appointmentTime\": \"78945489841445\",\n" +
            "            \"content\": \"根据您提交的病情描述及以往病历分析，初步诊断为扁桃体发炎、脓肿，建议就诊前先到医院检验科\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"327327237667777\",\n" +
            "            \"createTime\": \"22222222222\",\n" +
            "            \"doctId\": \"7\",\n" +
            "            \"doctorName\": \"朱震\",\n" +
            "            \"hospital\": \"苏州中医院\",\n" +
            "            \"department\": \"外科\",\n" +
            "            \"technicalPost\": \"主任医师\",\n" +
            "            \"appointmentTime\": \"78945489841445\",\n" +
            "            \"content\": \"根据您提交的病情描述及以往病历分析，初步诊断为扁桃体发炎、脓肿，建议就诊前先到医院检验科\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"327327237667777\",\n" +
            "            \"createTime\": \"22222222222\",\n" +
            "            \"doctId\": \"7\",\n" +
            "            \"doctorName\": \"朱震\",\n" +
            "            \"hospital\": \"苏州中医院\",\n" +
            "            \"department\": \"外科\",\n" +
            "            \"technicalPost\": \"主任医师\",\n" +
            "            \"appointmentTime\": \"78945489841445\",\n" +
            "            \"content\": \"根据您提交的病情描述及以往病历分析，初步诊断为扁桃体发炎、脓肿，建议就诊前先到医院检验科\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"327327237667777\",\n" +
            "            \"createTime\": \"22222222222\",\n" +
            "            \"doctId\": \"7\",\n" +
            "            \"doctorName\": \"朱震\",\n" +
            "            \"hospital\": \"苏州中医院\",\n" +
            "            \"department\": \"外科\",\n" +
            "            \"technicalPost\": \"主任医师\",\n" +
            "            \"appointmentTime\": \"78945489841445\",\n" +
            "            \"content\": \"根据您提交的病情描述及以往病历分析，初步诊断为扁桃体发炎、脓肿，建议就诊前先到医院检验科\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"327327237667777\",\n" +
            "            \"createTime\": \"22222222222\",\n" +
            "            \"doctId\": \"7\",\n" +
            "            \"doctorName\": \"朱震\",\n" +
            "            \"hospital\": \"苏州中医院\",\n" +
            "            \"department\": \"外科\",\n" +
            "            \"technicalPost\": \"主任医师\",\n" +
            "            \"appointmentTime\": \"78945489841445\",\n" +
            "            \"content\": \"根据您提交的病情描述及以往病历分析，初步诊断为扁桃体发炎、脓肿，建议就诊前先到医院检验科\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"327327237667455\",\n" +
            "            \"createTime\": \"22222222222\",\n" +
            "            \"doctId\": \"7\",\n" +
            "            \"doctorName\": \"朱震\",\n" +
            "            \"hospital\": \"苏州中医院\",\n" +
            "            \"department\": \"外科\",\n" +
            "            \"technicalPost\": \"主任医师\",\n" +
            "            \"appointmentTime\": \"78945489841445\",\n" +
            "            \"content\": \"根据您提交的病情描述及以往病历分析，初步诊断为扁桃体发炎、脓肿，建议就诊前先到医院检验科\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    public static UserSubscribeListFragment newInstance()
    {
        UserSubscribeListFragment fragment = new UserSubscribeListFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        getListView().setAdapter(ArrayAdapter.createFromResource(mActivity, R.array.array_menu, android.R.layout.simple_list_item_activated_1));
    }

    @Override
    public String getFragmentTitle()
    {
        return getString(R.string.tv_fragment_ptr_list);
    }

    @Override
    protected void doGetData()
    {
        mActivity.showProgressDialog();
        RequestUtils.getDataFromUrl(mActivity, "http://www.baidu.com", this);
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem)
    {
        Type type = new TypeToken<List<UserSubscribeItem>>()
        {
        }.getType();
        List<UserSubscribeItem> items = GsonUtils.jsonToResultItemToList(DATA, type);
        getListView().setAdapter(new SubscribeAdapter(mActivity, items));
    }

    @Override
    public void onGetDataFailured(String msg)
    {

    }
}
