package com.xinheng.adapter.user;

import android.view.View;

import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.mvp.model.user.UserChecktItem;

import java.util.List;
import java.util.Random;

/**
 * 我的检查列表适配器
 */
public class UserCheckListAdapter extends BaseAdapter<UserChecktItem>
{
    public UserCheckListAdapter(BaseActivity activity, List<UserChecktItem> data)
    {
        super(activity, R.layout.list_user_check_item, data);
    }

    @Override
    public void bindDataToView(View convertView, UserChecktItem item)
    {
         setTextViewText(convertView, R.id.tv_hospital_dept, item.name);
         setTextViewText(convertView, R.id.tv_text, item.name);
        if(UserChecktItem.STATUS_0.equals(item.consentState ))
        {
            setImageViewResId(convertView, R.id.iv_check_status, R.mipmap.ic_user_check_0);
        }
       else  if(UserChecktItem.STATUS_1.equals(item.consentState ))
        {
            setImageViewResId(convertView, R.id.iv_check_status, R.mipmap.ic_user_check_1);
        }
        else
        {
            setImageViewResId(convertView, R.id.iv_check_status, R.mipmap.ic_user_check_2);
        }
        String resName = "ic_user_medical_"+new Random().nextInt(5);
        int resId = getActivity().getResources().getIdentifier(resName,"mipmap",getActivity().getPackageName());
        if(resId > 0)
        {
            setImageViewResId(convertView, R.id.iv_image, resId);
        }
    }
}
