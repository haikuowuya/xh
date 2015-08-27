package com.xinheng.adapter.user;

import android.view.View;

import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.mvp.model.UserCounselItem;
import com.xinheng.mvp.model.UserCounselReplyItem;

import java.util.List;

/**
 * 我的咨询列表适配器
 */
public class UserCounselListAdapter extends BaseAdapter<UserCounselItem>
{
    public UserCounselListAdapter(BaseActivity activity, List<UserCounselItem> data)
    {
        super(activity, R.layout.list_user_counsel_item, data);
    }

    @Override
    public void bindDataToView(View convertView, UserCounselItem item)
    {
        UserCounselReplyItem reply = item.replys.get(0);
        setTextViewText(convertView, R.id.tv_answer_count, "医生回答（" + item.total + "）");
        setTextViewText(convertView, R.id.tv_counsel_question, item.question);
        setTextViewText(convertView, R.id.tv_counsel_time, item.createTime);
        setTextViewText(convertView, R.id.tv_counsel_desc, item.question);
        setTextViewText(convertView, R.id.tv_answer_time, reply.createTime);
        setTextViewText(convertView, R.id.tv_doctor_info, reply.doctorName + " " + reply.hospital + "  " + reply.department + "/" + reply.technicalPost);
        setTextViewText(convertView, R.id.tv_answer_content, reply.content);
    }
}