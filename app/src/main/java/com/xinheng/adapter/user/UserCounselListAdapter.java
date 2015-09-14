package com.xinheng.adapter.user;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.mvp.model.UserCounselItem;
import com.xinheng.mvp.model.UserCounselReplyItem;
import com.xinheng.util.DateFormatUtils;

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
        setTextViewText(convertView, R.id.tv_counsel_question, item.title);
        String counselTime = DateFormatUtils.format(item.createTime);
        setTextViewText(convertView, R.id.tv_counsel_time, counselTime);
        setTextViewText(convertView, R.id.tv_counsel_desc, item.question);
        int total = 0;
        if (TextUtils.isDigitsOnly(item.total))
        {
            total = Integer.parseInt(item.total);
        }
        setTextViewText(convertView, R.id.tv_answer_count, "医生回答(" +total + ")");
        setViewVisibility(convertView, R.id.linear_doctor_answer_container, View.GONE);
        if (total > 0)
        {
            setViewVisibility(convertView, R.id.linear_doctor_answer_container, View.VISIBLE);
            if (item.replys != null && !item.replys.isEmpty())
            {
                UserCounselReplyItem reply = item.replys.get(0);
                String answerTime = DateFormatUtils.format(reply.createTime);
                setTextViewText(convertView, R.id.tv_answer_time, answerTime);
                String doctorInfo = reply.doctName + " " + reply.hospital + "  " + reply.department + "/" + reply.technicalPost;
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(doctorInfo);
                ForegroundColorSpan span = new ForegroundColorSpan(0xFF2FAD68);
                spannableStringBuilder.setSpan(span, 0, reply.doctName.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                setTextViewText(convertView, R.id.tv_doctor_info, spannableStringBuilder);
                setTextViewText(convertView, R.id.tv_answer_content, reply.content);
            }

            if(total >1)
            {
                setViewVisibility(convertView, R.id.tv_more, View.VISIBLE);
                setViewOnClick(convertView, R.id.tv_more, new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        getActivity().showCroutonToast("正在完善中……");
                    }
                });
            }
        }
    }
}
