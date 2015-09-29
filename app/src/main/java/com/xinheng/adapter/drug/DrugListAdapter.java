package com.xinheng.adapter.drug;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinheng.APIURL;
import com.xinheng.R;
import com.xinheng.common.AbsImageLoadingListener;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.base.ViewHolder;
import com.xinheng.mvp.model.prescription.DrugItem;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/15 0015.
 */
public class DrugListAdapter extends BaseAdapter<DrugItem>
{
    private LinkedList<DrugItem> mSelectedItems = new LinkedList<>();

    public DrugListAdapter(BaseActivity activity, List<DrugItem> data)
    {
        super(activity, R.layout.list_drug_item, data);
    }

    public LinkedList<DrugItem> getSelectedItems()
    {
        return mSelectedItems;
    }

    @Override
    public void bindDataToView(View convertView, final DrugItem drugItem)
    {
        setTextViewText(convertView, R.id.tv_drug_name, drugItem.name);
        String drugInfo = "产地:" + drugItem.place + " 规格:" + drugItem.specs + " 价格:" + drugItem.cost;
        setTextViewText(convertView, R.id.tv_drug_info, drugInfo);
        final ImageView imageView = ViewHolder.getView(convertView, R.id.civ_image);
        String img = drugItem.img;
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
        final TextView tvAdd = ViewHolder.getView(convertView, R.id.tv_add);
        if (mSelectedItems.contains(drugItem))
        {
            tvAdd.setText("取消");
            tvAdd.setBackgroundResource(R.drawable.btn_cancle_click_gray_selector);
        }
        else
        {
            tvAdd.setText("加入处方");
            tvAdd.setBackgroundResource(R.drawable.btn_register_code_selector);
        }
        tvAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if ("取消".equals(tvAdd.getText().toString()))
                {
                    mSelectedItems.remove(drugItem);
                    tvAdd.setText("加入处方");
                    tvAdd.setBackgroundResource(R.drawable.btn_register_code_selector);
                }
                else
                {
                    tvAdd.setText("取消");
                    mSelectedItems.add(drugItem);
                    tvAdd.setBackgroundResource(R.drawable.btn_cancle_click_gray_selector);
                }
            }
        });
    }
}
