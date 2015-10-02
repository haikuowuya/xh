package com.xinheng.adapter.depart;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinheng.APIURL;
import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.base.ViewHolder;
import com.xinheng.common.AbsImageLoadingListener;
import com.xinheng.mvp.model.depart.DepartDoctorItem;
import com.xinheng.view.CircularImageView;

import java.util.List;

/**
 * 科室医生列表适配器
 */
public class DepartDoctorListAdapter extends BaseAdapter<DepartDoctorItem>
{
    public DepartDoctorListAdapter(BaseActivity activity, List<DepartDoctorItem> data)
    {
        super(activity, R.layout.list_depart_doctor_item, data);
    }

    @Override
    public void bindDataToView(View convertView, DepartDoctorItem departDoctorItem)
    {
        setTextViewText(convertView, R.id.tv_skill, departDoctorItem.skill);
        setTextViewText(convertView, R.id.tv_doctor_name, departDoctorItem.doctName);
        setTextViewText(convertView, R.id.tv_technical_post, departDoctorItem.technicalPost);
        setTextViewText(convertView, R.id.tv_dept_name, departDoctorItem.department);
        final CircularImageView imageView = ViewHolder.getView(convertView, R.id.civ_image);
        TextView tvAppointment = ViewHolder.getView(convertView, R.id.tv_action_subscribe);
        if("-1".equals(departDoctorItem.status))
        {
            tvAppointment.setText("约  满");
        }
        else
        {
            tvAppointment.setText("有  号");
        }
        String img = departDoctorItem.img;
        if (!TextUtils.isEmpty(img))
        {
            if (!img.startsWith(APIURL.BASE_API_URL))
            {
                img = APIURL.BASE_API_URL + img;
            }
            imageView.setTag(img);
            ImageLoader.getInstance().loadImage(img, new AbsImageLoadingListener()
            {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
                {
                    if (null != loadedImage && imageUri.equals(imageView.getTag()))
                    {
                        imageView.setImageBitmap(loadedImage);
                    }
                }
            });
        }
    }
}
