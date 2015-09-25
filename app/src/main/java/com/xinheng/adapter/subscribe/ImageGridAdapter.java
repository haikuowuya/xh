package com.xinheng.adapter.subscribe;

import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;

import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.base.ViewHolder;
import com.xinheng.view.CircularImageView;

import java.util.List;

/**
 * Created by Steven on 2015/9/25 0025.
 */
public class ImageGridAdapter extends BaseAdapter<String>
{
    public ImageGridAdapter(BaseActivity activity, List<String> data)
    {
        super(activity, R.layout.grid_subscribe_image_item, data);
    }

    @Override
    public void bindDataToView(View convertView, String s)
    {
        CircularImageView circularImageView = ViewHolder.getView(convertView, R.id.civ_image);
        if (TextUtils.isEmpty(s))
        {
            circularImageView.setImageResource(R.mipmap.ic_add_recipe);
        } else
        {
            circularImageView.setImageBitmap(BitmapFactory.decodeFile(s));
        }
    }
}
