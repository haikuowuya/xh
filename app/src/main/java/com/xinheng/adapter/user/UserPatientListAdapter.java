package com.xinheng.adapter.user;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.mvp.model.user.UserPatientItem;

import java.util.List;

/**
 * 我的就诊人列表适配器
 */
public class UserPatientListAdapter extends BaseAdapter<UserPatientItem>
{
    public static final String FLAG_4_BLANK = "    ";

    public UserPatientListAdapter(BaseActivity activity, List<UserPatientItem> data)
    {
        super(activity, R.layout.list_user_patient_item, data);
    }

    @Override
    public void bindDataToView(View convertView, UserPatientItem item)
    {
        String name = item.name + FLAG_4_BLANK;
        String sex = "女";
        if ("1".equals(item.sex))
        {
            sex = "男";
        }
        String info = name + sex + FLAG_4_BLANK + item.age;
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(info);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(0xFF666666), name.length(), info.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        setTextViewText(convertView, R.id.tv_patient_name, spannableStringBuilder);
        String fakeIdCard = item.idcard;
        if (!TextUtils.isEmpty(fakeIdCard) && fakeIdCard.length() > 7)
        {
            fakeIdCard = fakeIdCard.substring(0, 3) + " * * * * * * " + fakeIdCard.substring(fakeIdCard.length() - 4);
        }
        String idCard = "身份证" + FLAG_4_BLANK + fakeIdCard;
        setTextViewText(convertView, R.id.tv_patient_card, idCard);
        String fakeMobile = item.mobile;
        if (!TextUtils.isEmpty(fakeMobile) && fakeMobile.length() > 7)
        {
            fakeMobile = fakeMobile.substring(0, 3) + " * * * * * " + fakeMobile.substring(fakeMobile.length() - 3);
        }
        String mobile = "手机号" + FLAG_4_BLANK + fakeMobile;
        setTextViewText(convertView, R.id.tv_phone, mobile);
        if ("1".equals(item.isDefault))
        {
            setViewVisibility(convertView, R.id.tv_default, View.VISIBLE);
        }
        else
        {
            setViewVisibility(convertView, R.id.tv_default, View.GONE);
        }
    }
}
