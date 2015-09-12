package com.xinheng.adapter.user;

import android.view.View;

import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.mvp.model.UserRecipeItem;

import java.util.List;
import java.util.Random;

/**
 * 我的处方列表适配器
 */
public class UserRecipeListAdapter extends BaseAdapter<UserRecipeItem>
{
    public UserRecipeListAdapter(BaseActivity activity, List<UserRecipeItem> data)
    {
        super(activity, R.layout.list_user_recipe_item, data);
    }

    @Override
    public void bindDataToView(View convertView, UserRecipeItem item)
    {
         setTextViewText(convertView, R.id.tv_hospital_dept, item.name);
         setTextViewText(convertView, R.id.tv_text, item.name);
        if("1".equals(item.payState ))
        {
            setImageViewResId(convertView, R.id.iv_recipe_status, R.mipmap.ic_user_recipe_1);
        }
        else
        {
            setImageViewResId(convertView, R.id.iv_recipe_status, R.mipmap.ic_user_recipe_0);
        }
        String resName = "ic_user_medical_"+new Random().nextInt(5);
        int resId = getActivity().getResources().getIdentifier(resName,"mipmap",getActivity().getPackageName());
        if(resId > 0)
        {
            setImageViewResId(convertView, R.id.iv_image, resId);
        }
    }
}
