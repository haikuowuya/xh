package com.xinheng.adapter.user;

import android.view.View;

import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.mvp.model.user.UserMedicalItem;

import java.util.List;
import java.util.Random;

/**
 * 我的病历列表适配器
 */
public class UserMedicalListAdapter extends BaseAdapter<UserMedicalItem>
{
    public UserMedicalListAdapter(BaseActivity activity, List<UserMedicalItem> data)
    {
        super(activity, R.layout.list_user_medical_item, data);
    }

    @Override
    public void bindDataToView(View convertView, UserMedicalItem item)
    {
         setTextViewText(convertView, R.id.tv_hospital_dept, item.name);
         setTextViewText(convertView, R.id.tv_text, item.name);
        String resName = "ic_user_medical_"+new Random().nextInt(5);
        int resId = getActivity().getResources().getIdentifier(resName,"mipmap",getActivity().getPackageName());
        if(resId > 0)
        {
            setImageViewResId(convertView, R.id.iv_image, resId);
        }
    }
}
