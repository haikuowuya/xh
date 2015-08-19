package com.xinheng;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.xinheng.adapter.user.SubscribeAdapter;
import com.xinheng.base.BaseActivity;
import com.xinheng.mvp.model.UserSubscribeItemHolder;
import com.xinheng.util.DensityUtils;
import com.xinheng.util.GsonUtils;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：我的预约
 */
public class UserSubscribeActivity extends BaseActivity
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
    private ListView mListView;

    public static void actionUserSubscribe(BaseActivity activity)
    {
        Intent intent = new Intent(activity, UserSubscribeActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_subscribe);//TODO
        initView();
        setIvRightVisibility(View.VISIBLE);
        UserSubscribeItemHolder userSubscribeItemHolder = GsonUtils.jsonToClass(DATA, UserSubscribeItemHolder.class);
        if (null != userSubscribeItemHolder && !userSubscribeItemHolder.propertise.isEmpty())
        {
            mListView.setAdapter(new SubscribeAdapter(mActivity, userSubscribeItemHolder.propertise));
        }
    }

    private void initView()
    {
        mListView = (ListView) findViewById(R.id.lv_listview);
        mListView.setDivider(new ColorDrawable(getResources().getColor(R.color.color_content_background_color)));
        mListView.setDividerHeight(DensityUtils.dpToPx(mActivity, 10.f));
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_user_subscribe);
    }
}
