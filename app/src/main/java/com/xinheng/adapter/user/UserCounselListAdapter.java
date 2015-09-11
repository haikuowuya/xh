package com.xinheng.adapter.user;

import android.view.View;

import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.mvp.model.UserCounselItem;
import com.xinheng.mvp.model.UserCounselReplyItem;
import com.xinheng.util.DateFormatUtils;

import java.text.SimpleDateFormat;
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        setTextViewText(convertView, R.id.tv_answer_count, "医生回答（" + item.total + "）");
        setTextViewText(convertView, R.id.tv_counsel_question, item.question);
        String counselTime = DateFormatUtils.format(item.createTime);
        setTextViewText(convertView, R.id.tv_counsel_time, counselTime);
        setTextViewText(convertView, R.id.tv_counsel_desc, item.question);
        if (item.replys != null && !item.replys.isEmpty())
        {
            UserCounselReplyItem reply = item.replys.get(0);
            String answerTime =  DateFormatUtils.format(reply.createTime);
            setTextViewText(convertView, R.id.tv_answer_time, answerTime);
            setTextViewText(convertView, R.id.tv_doctor_info, reply.doctName + " " + reply.hospital + "  " + reply.department + "/" + reply.technicalPost);
            setTextViewText(convertView, R.id.tv_answer_content, reply.content);
        }
    }
}
